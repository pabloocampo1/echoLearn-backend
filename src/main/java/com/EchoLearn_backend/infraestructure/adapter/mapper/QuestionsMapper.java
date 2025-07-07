package com.EchoLearn_backend.infraestructure.adapter.mapper;

import com.EchoLearn_backend.domain.model.AnswerModel;
import com.EchoLearn_backend.domain.model.ExamModel;
import com.EchoLearn_backend.domain.model.QuestionModel;
import com.EchoLearn_backend.domain.port.ExamPersistencePort;
import com.EchoLearn_backend.infraestructure.adapter.entity.AnswerExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.entity.ExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.entity.QuestionsExamEntity;
import com.sun.jdi.LongValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class QuestionsMapper {
    private final AnswerMapper answerMapper;
    private final ExamMapper examMapper;
    private final ExamPersistencePort examPersistencePort;

    @Autowired
    public QuestionsMapper(AnswerMapper answerMapper, ExamMapper examMapper, ExamPersistencePort examPersistencePort) {
        this.answerMapper = answerMapper;
        this.examMapper = examMapper;
        this.examPersistencePort = examPersistencePort;
    }

    public QuestionModel toModel(QuestionsExamEntity questionsExamEntity) {
        List<AnswerModel> answerModels = questionsExamEntity.getAnswers()
                .stream().map(this.answerMapper::toModel).toList();

        return QuestionModel
                .builder()
                .id_question(questionsExamEntity.getId_question())
                .question(questionsExamEntity.getQuestion())
                .available(questionsExamEntity.getAvailable())
                .answerModels(answerModels)
                .exam_id(questionsExamEntity.getExam().getId_exam().intValue())
                .build();
    }

    public QuestionsExamEntity toDbo(QuestionModel questionModel){
        ExamModel examModel = this.examPersistencePort.getById(questionModel.getExam_id().longValue());
        ExamEntity examEntity = this.examMapper.modelToEntity(examModel);

        List<AnswerExamEntity> answerExamEntityList = Collections.emptyList();
        if (questionModel.getAnswerModels() != null) {
            answerExamEntityList = questionModel.getAnswerModels()
                    .stream()
                    .map(this.answerMapper::toDbo)
                    .toList();
        }
        return QuestionsExamEntity
                .builder()
                .id_question(questionModel.getId_question())
                .exam(examEntity)
                .question(questionModel.getQuestion())
                .available(questionModel.getAvailable())
                .answers(answerExamEntityList)
                .build();
    }

}
