package com.EchoLearn_backend.application.service.Exam;

import com.EchoLearn_backend.Exception.BadRequestException;
import com.EchoLearn_backend.application.usecases.ExamUseCase;
import com.EchoLearn_backend.domain.model.AnswerModel;
import com.EchoLearn_backend.domain.model.ExamModel;
import com.EchoLearn_backend.domain.model.QuestionModel;
import com.EchoLearn_backend.domain.port.AnswerPersistencePort;
import com.EchoLearn_backend.domain.port.ExamPersistencePort;
import com.EchoLearn_backend.domain.port.QuestionPersistencePort;
import com.EchoLearn_backend.infraestructure.adapter.entity.QuestionsExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.mapper.ExamMapper;
import com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ExamService implements ExamUseCase {

    // Create the levels as a variable in the service for validate
    private final Integer pointsLevelEasy = 300;
    private final Integer pointsLevelIntermediate = 500;
    private final Integer  pointsLevelHard = 900;

    private final ExamPersistencePort examPersistencePort;
    private final QuestionPersistencePort questionPersistencePort;
    private final AnswerPersistencePort answerPersistencePort;
    private final ExamMapper examMapper;


    @Autowired
    public ExamService(ExamPersistencePort examPersistencePort, QuestionPersistencePort questionPersistencePort, AnswerPersistencePort answerPersistencePort, ExamMapper examMapper) {
        this.examPersistencePort = examPersistencePort;
        this.questionPersistencePort = questionPersistencePort;
        this.answerPersistencePort = answerPersistencePort;
        this.examMapper = examMapper;
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
    public ExamResult evaluateExamResult(ExamResultRequest examResultRequest) {
        return null;
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
}
