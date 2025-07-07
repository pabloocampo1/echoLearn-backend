package com.EchoLearn_backend.infraestructure.adapter.mapper;

import com.EchoLearn_backend.domain.model.AnswerModel;
import com.EchoLearn_backend.domain.model.QuestionModel;
import com.EchoLearn_backend.domain.port.QuestionPersistencePort;
import com.EchoLearn_backend.infraestructure.adapter.entity.AnswerExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.entity.QuestionsExamEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class AnswerMapper {
    private final QuestionPersistencePort questionPersistencePort;
    private final QuestionsMapper questionsMapper;

    @Autowired
    public AnswerMapper(@Lazy QuestionPersistencePort questionPersistencePort,@Lazy QuestionsMapper questionsMapper) {
        this.questionPersistencePort = questionPersistencePort;
        this.questionsMapper = questionsMapper;
    }

    public AnswerModel toModel(AnswerExamEntity answerExamEntity) {
        return AnswerModel
                .builder()
                .id_answer(answerExamEntity.getId_answer())
                .id_question(answerExamEntity.getQuestion().getId_question())
                .answerText(answerExamEntity.getAnswerText())
                .isCorrect(answerExamEntity.getIsCorrect())
                .build();
    }

    public AnswerExamEntity toDbo(AnswerModel answerModel) {
        QuestionModel questionModel = this.questionPersistencePort.findById(answerModel.getId_question());
        return AnswerExamEntity
                .builder()
                .id_answer(answerModel.getId_answer())
                .answerText(answerModel.getAnswerText())
                .question(this.questionsMapper.toDbo(questionModel))
                .isCorrect(answerModel.getIsCorrect())
                .build();
    }
}
