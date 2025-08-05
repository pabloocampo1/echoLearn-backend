package com.EchoLearn_backend.application.service.Exam;

import com.EchoLearn_backend.Exception.BadRequestException;


import com.EchoLearn_backend.Exception.ResourceNotFoundException;
import com.EchoLearn_backend.application.usecases.ExamUseCase;
import com.EchoLearn_backend.application.usecases.User.UserUseCases;
import com.EchoLearn_backend.domain.model.*;
import com.EchoLearn_backend.domain.port.AnswerPersistencePort;
import com.EchoLearn_backend.domain.port.ApprovedExamPersistencePort;
import com.EchoLearn_backend.domain.port.ExamPersistencePort;
import com.EchoLearn_backend.domain.port.QuestionPersistencePort;
import com.EchoLearn_backend.infraestructure.adapter.adapterJpaImpl.ProfileJpaAdapter;
import com.EchoLearn_backend.infraestructure.adapter.entity.ApprovedExam;
import com.EchoLearn_backend.infraestructure.adapter.entity.ProfileEntity;
import com.EchoLearn_backend.infraestructure.adapter.mapper.ExamMapper;
import com.EchoLearn_backend.infraestructure.adapter.mapper.UserDboMapper;
import com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos.*;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ExamService implements ExamUseCase {

    // Create the levels as a variable in the service for validate
    private final Integer extraPoints = 300;


    private final ExamPersistencePort examPersistencePort;
    private final QuestionPersistencePort questionPersistencePort;
    private final AnswerPersistencePort answerPersistencePort;
    private final ExamMapper examMapper;
    private final UserUseCases userUseCases;
    private final UserDboMapper userDboMapper;
    private final ProfileJpaAdapter profileJpaAdapter;
    private final ApprovedExamPersistencePort approvedExamPersistencePort;



    @Autowired
    public ExamService(ExamPersistencePort examPersistencePort, QuestionPersistencePort questionPersistencePort, AnswerPersistencePort answerPersistencePort, ExamMapper examMapper, UserUseCases userUseCases, UserDboMapper userDboMapper, ProfileJpaAdapter profileJpaAdapter, ApprovedExamPersistencePort approvedExamPersistencePort) {
        this.examPersistencePort = examPersistencePort;
        this.questionPersistencePort = questionPersistencePort;
        this.answerPersistencePort = answerPersistencePort;
        this.examMapper = examMapper;
        this.userUseCases = userUseCases;
        this.userDboMapper = userDboMapper;
        this.profileJpaAdapter = profileJpaAdapter;
        this.approvedExamPersistencePort = approvedExamPersistencePort;
    }

    @Override
    public ExamModel saveSingleExam(ExamModel examModel) {
        return this.examPersistencePort.save(examModel);
    }

    @Override
    public ExamModel getById( @Valid  Long id) {
        ExamModel exam = this.examPersistencePort.getById(id);
        Long exam_id = exam.getId_exam();
        exam.setQuestions(this.getAllQuestionByExamId(exam_id));
        return exam;
    }

    @Override
    @Transactional
    public ExamModel saveExam(@Valid ExamCreateDto examCreateDto) {

        try {
            this.validateDataBasic(examCreateDto);

            // the first step are create a single exam (without relationship question) and create the relationship with subcategories
            ExamModel examModelInitial = this.examMapper.dtoToModel(examCreateDto);
            ExamModel examSave = this.examPersistencePort.save(examModelInitial);

            // cretae the question with answer and validate if isCorrect no repeact
            this.saveQuestionAndAnswers(examCreateDto, examSave.getId_exam());

            return examSave;
        }catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<ExamModel> getAll() {
        return this.examPersistencePort.getAll();
    }

    @Override
    public List<ExamHomeDto> getAllExamForHome() {
        return this.examPersistencePort.getAllExamForHome();
    }

    @Override
    public List<ExamHomeDto> getAllExamBySubcategory(@Valid Integer id) {
        return this.examPersistencePort.findAllBySubcategory(id);
    }

    @Override
    public QuestionModel saveQuestion(QuestionModel questionModel) {
        return this.questionPersistencePort.save(questionModel);
    }

    @Override
    public AnswerModel saveAnswer(AnswerModel answerModel) {
        return this.answerPersistencePort.save(answerModel);
    }

    @Override
    public List<QuestionModel> getAllQuestionByExamId(Long id) {
        return this.questionPersistencePort.findAllByExamId(id);
    }

    @Override
    public ExamQuestionsResponse getQuestionOfOneExam(Long exam_id) {

        if(!this.existExamByExamId(exam_id)){
            throw new ResourceNotFoundException("No found the exam.");
        }

        ExamQuestionsResponse examQuestionsResponse = new ExamQuestionsResponse();
        examQuestionsResponse.setExam_id(exam_id);

        List<QuestionModel> questionModelList = this.getAllQuestionByExamId(exam_id);

       List<QuestionResponse> questionResponseList = questionModelList
                .stream()
                .map(questionModel -> {
                    QuestionResponse questionResponse = new QuestionResponse();
                    questionResponse.setQuestion_id(questionModel.getId_question());
                    questionResponse.setType(questionModel.getType());
                    questionResponse.setQuestionText(questionModel.getQuestion());
                    questionResponse.setAnswerResponseList(
                            questionModel.getAnswerModels()
                                    .stream()
                                    .map(answerModel -> {
                                        AnswerResponse answerResponse = new AnswerResponse();
                                        answerResponse.setAnswer_id(answerModel.getId_answer());
                                        answerResponse.setAnswerText(answerModel.getAnswerText());
                                        return answerResponse;

                                    })
                                    .toList()
                    );
                    return questionResponse;

                })
                .toList();

       examQuestionsResponse.setQuestionResponseList(questionResponseList);



        //
        return examQuestionsResponse;
    }

    @Override
    public Boolean existExamByExamId(Long exam_id) {
        return this.examPersistencePort.exitsExam(exam_id);
    }


    @Transactional
    public List<QuestionModel> saveQuestionAndAnswers(@Valid ExamCreateDto examCreateDto, @Valid Long id_exam) {

            List<QuestionCreateExamDto> questionCreateExamDto = examCreateDto.getQuestions();

            List<QuestionModel> questionModelList = questionCreateExamDto
                    .stream().
                    map((question) -> {
                        QuestionModel questionModel = new QuestionModel();
                        questionModel.setAvailable(question.getAvailable());
                        questionModel.setQuestion(question.getQuestion());
                        questionModel.setType(question.getType());
                        questionModel.setExam_id(id_exam.intValue());
                        questionModel.setAnswerModels(new ArrayList<>());
                        // commentary
                        QuestionModel questionModelSaved = this.saveQuestion(questionModel);


                        List<AnswerModel> answerModelList = question.getAnswers()
                                .stream()
                                .map((answer -> {
                                    AnswerModel answerModel = new AnswerModel();
                                    answerModel.setAnswerText(answer.getAnswerText());
                                    answerModel.setIsCorrect(answer.getIsCorrect());
                                    answerModel.setId_question(questionModelSaved.getId_question());
                                    return this.saveAnswer(answerModel);
                                })).toList();
                        int counterAnswersCorrects = (int) answerModelList.stream().filter(AnswerModel::getIsCorrect).limit(2).count();
                        if(counterAnswersCorrects >= 2){
                            throw new BadRequestException("Not can to be more than 2 answers corrects in one question.");
                        }

                        return questionModelSaved;
                    }).toList();




            return questionModelList;

    }

    public void validateDataBasic(ExamCreateDto examCreateDto) {
        // validate the points
        if(examCreateDto.getPoints() >= 2000) {
            throw new BadRequestException("You don't can add more than 2000 points in an exam.");
        }

        // validate the duration
        if(examCreateDto.getDuration() >= 60) {
            throw new BadRequestException("You don't can add more than 60 minutes of duration in an exam.");
        }

        // Validate if there are one question with one question correct
        examCreateDto.getQuestions().forEach((question -> {
            AtomicInteger answerWithoutOneOptionCorrectCounter = new AtomicInteger(0);
            question.getAnswers().forEach(answer -> {
                if (!answer.getIsCorrect()) answerWithoutOneOptionCorrectCounter.set(answerWithoutOneOptionCorrectCounter.get() + 1);
            });

            if(answerWithoutOneOptionCorrectCounter.get() == 0) {
                throw new BadRequestException("You need one answer correct in the question : " + question.getQuestion());
            }
        }));
    }



    @Override
    @Transactional
    public ExamResult evaluateExamResult(@Valid ExamSubmissionDTO examResultRequest) {

        // create the exam result to return
        ExamResult examResultResponse = new ExamResult();

        // get the exam complete
        ExamModel exam = this.getById(examResultRequest.getExam_id());

        // validate if the user exist and gather the user and profile
        User user = this.userUseCases.getById(examResultRequest.getId_user());
        ProfileEntity userProfile = this.profileJpaAdapter.getById(user.getProfile_id());

        // question of the exam
        List<QuestionModel> questionModelList = exam.getQuestions();
        // answers to validate
        List<AnswerSubmissionDto> answerSubmissionDtoList = examResultRequest.getAnswers();


        AtomicInteger questionsCorrect = new AtomicInteger();
        AtomicInteger questionsIncorrect = new AtomicInteger();
        List<ResultPerQuestionDto> resultForQuestion = new ArrayList<>();
        answerSubmissionDtoList.forEach(answerSubmissionDto -> {
            Optional<QuestionModel> question = questionModelList.stream().filter(
                    questionModel -> Objects.equals(questionModel.getId_question(), answerSubmissionDto.getId_question())
            ).findFirst();

            if (question.isPresent()){
                ResultPerQuestionDto result = new ResultPerQuestionDto();
                result.setQuestion(question.get().getQuestion());
                result.setQuestionSelectForTheUser(answerSubmissionDto.getAnswerSelect());
               Optional<AnswerModel> answerCorrect =  question.get().getAnswerModels()
                       .stream()
                       .filter(answerModel -> answerModel.getIsCorrect() == true)
                       .findFirst();
               // build the dto for retorn question en general

               if(answerCorrect.get().getAnswerText().equals(answerSubmissionDto.getAnswerSelect())){
                   questionsCorrect.getAndIncrement();
                   result.setIsCorrect(true);
               }else {
                   questionsIncorrect.getAndIncrement();
                   result.setIsCorrect(false);
               }

               resultForQuestion.add(result);
            }

        });

        examResultResponse.setResultOfQuestions(resultForQuestion);


        int totalOfQuestions = exam.getQuestions().size();
        int totalQuestionCorrects = questionsCorrect.get();
        int totalQuestionInCorrects = questionsIncorrect.get();
        double examResult = (double) totalQuestionCorrects / totalOfQuestions * 100;;


       // build the exam response
        examResultResponse.setExamTitle(exam.getTitle());
        examResultResponse.setExamId(exam.getId_exam());
        examResultResponse.setUserId(user.getUser_id());
        examResultResponse.setNameUser(userProfile.getName());
        examResultResponse.setNumberOfQuestionCorrect(totalQuestionCorrects);
        examResultResponse.setNumberOfQuestionIncorrect(totalQuestionInCorrects);
        examResultResponse.setDate(LocalDateTime.now());
        examResultResponse.setDurationOfTime(examResultRequest.getDurationOfTest());
        examResultResponse.setMinimumPassingScore(
                switch (exam.getLevel()) {
                    case "hard" -> 90;
                    case "intermediate" -> 80;
                    case "easy" -> 70;
                    default -> 0;
                }
        );
        examResultResponse.setResultExam(examResult);

        examResultResponse.setIsApproved(
                this.validateIfWinTheTestByLevelAndResult(exam.getLevel(), examResult)
        );


        // add extra points to according the level of the test
        int finalPoints = 0;


        if (examResultResponse.getIsApproved()) {
            finalPoints = exam.getPoints();

            if (examResult >= 100) {
                finalPoints += extraPoints;
            }
        }
        // change the return of approved
        examResultResponse.setPoints(finalPoints);

        // information of the count

        userProfile.setPoints(userProfile.getPoints() + finalPoints);
        userProfile.setNum_exams_takes(userProfile.getNum_exams_takes() + 1);
        if(examResultResponse.getIsApproved()){
            userProfile.setWins(userProfile.getWins() + 1);
        }else{
            userProfile.setFailed(userProfile.getFailed() + 1);
        }

        // save information of the profile
        this.profileJpaAdapter.save(userProfile);

        // save the exam approved
       if(examResultResponse.getIsApproved()) {
           ApprovedExam approvedExam = new ApprovedExam();
           approvedExam.setExamEntity(this.examMapper.modelToEntity(exam));
           approvedExam.setUser(this.userDboMapper.toDbo(user));
           approvedExam.setScore(examResultResponse.getPoints());
           approvedExam.setIsApproved(examResultResponse.getIsApproved());

           this.approvedExamPersistencePort.save(approvedExam);
       }

    return examResultResponse;
    }

    public Boolean validateIfWinTheTestByLevelAndResult(String level, Double examResult) {
        switch (level.toLowerCase()) {
            case "easy":
                return examResult >= 70;
            case "intermediate":
                return examResult >= 80;
            case "hard":
                return examResult >= 90;
            default:
                return false;
        }
    }
}

// para despues, traer los examenes, y mostrar si gano o no uno y retornarle eso al front.
