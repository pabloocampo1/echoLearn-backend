package com.EchoLearn_backend.application.service.Exam;

import com.EchoLearn_backend.application.usecases.ExamUseCase;
import com.EchoLearn_backend.application.usecases.SubCategoryUseCase.SubCategoryUseCase;
import com.EchoLearn_backend.domain.model.AnswerModel;
import com.EchoLearn_backend.domain.model.ExamModel;
import com.EchoLearn_backend.domain.model.QuestionModel;
import com.EchoLearn_backend.domain.model.SubCategory;
import com.EchoLearn_backend.domain.port.AnswerPersistencePort;
import com.EchoLearn_backend.domain.port.ExamPersistencePort;
import com.EchoLearn_backend.domain.port.QuestionPersistencePort;
import com.EchoLearn_backend.infraestructure.adapter.entity.SubCategoryExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.mapper.ExamMapper;
import com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos.ExamCreateDto;
import com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos.QuestionCreateExamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamService implements ExamUseCase {

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
    @Transactional
    public void saveExam(ExamCreateDto examCreateDto) {

        try {
            // the first step are create a single exam (without relationship question) and create the relationship with subcategories
            ExamModel examModelInitial = this.examMapper.dtoToModel(examCreateDto);
            ExamModel examSave = this.examPersistencePort.save(examModelInitial);

            // create all necesary logic when we want to create an exam single
            // have already an exam created, we're going to save the answers and question
            this.saveQuestionAndAnswers(examCreateDto, examSave.getId_exam());
            // lo que falta : crear logica en el metoo de save question and answers, for one of them answer have to be correct and thta logic. boru.

            // save the exam with what questions

            // create logic of validation for entities that goes here


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public QuestionModel saveQuestion(QuestionModel questionModel) {
        return this.questionPersistencePort.save(questionModel);
    }

    @Override
    public AnswerModel saveAnswer(AnswerModel answerModel) {
        return this.answerPersistencePort.save(answerModel);
    }


    public List<QuestionModel> saveQuestionAndAnswers(ExamCreateDto examCreateDto, Long id_exam) {
        try {
            List<QuestionCreateExamDto> questionCreateExamDto = examCreateDto.getQuestions();

            // commentary
            List<QuestionModel> questionModelList = questionCreateExamDto
                    .stream().
                    map((question) -> {
                        QuestionModel questionModel = new QuestionModel();
                        questionModel.setAvailable(question.getAvailable());
                        questionModel.setQuestion(question.getQuestion());
                        questionModel.setExam_id(id_exam.intValue());
                        questionModel.setAnswerModels(new ArrayList<>());
                        System.out.println("question after to save:  " + questionModel);
                        // commentary
                        QuestionModel questionModelSaved = this.saveQuestion(questionModel);
                        System.out.println("question before to save:  " + questionModelSaved);
                        List<AnswerModel> answerModelList = question.getAnswers()
                                .stream()
                                .map((answer -> {
                                    AnswerModel answerModel = new AnswerModel();
                                    answerModel.setAnswerText(answer.getAnswerText());
                                    answerModel.setIsCorrect(answer.getIsCorrect());
                                    answerModel.setId_question(questionModelSaved.getId_question());
                                    return this.saveAnswer(answerModel);
                                })).toList();



                        return questionModelSaved;
                    }).toList();


            return questionModelList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
