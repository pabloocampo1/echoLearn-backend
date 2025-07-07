package com.EchoLearn_backend.infraestructure.adapter.adapterJpaImpl;

import com.EchoLearn_backend.domain.model.QuestionModel;
import com.EchoLearn_backend.domain.port.QuestionPersistencePort;
import com.EchoLearn_backend.infraestructure.adapter.entity.QuestionsExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.mapper.QuestionsMapper;
import com.EchoLearn_backend.infraestructure.adapter.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionJpaAdapter implements QuestionPersistencePort {
    private final QuestionRepository questionRepository;
    private final QuestionsMapper questionsMapper;

    @Autowired
    public QuestionJpaAdapter(QuestionRepository questionRepository, QuestionsMapper questionsMapper) {
        this.questionRepository = questionRepository;
        this.questionsMapper = questionsMapper;
    }


    @Override
    public QuestionModel save(QuestionModel questionModel) {
        QuestionsExamEntity questionsExamEntity = this.questionsMapper.toDbo(questionModel);
        QuestionsExamEntity questionsExamEntitySaved = this.questionRepository.save(questionsExamEntity);

        return this.questionsMapper.toModel(questionsExamEntitySaved);
    }

    @Override
    public QuestionModel findById(Integer id) {
        QuestionsExamEntity questionsExamEntity = this.questionRepository.findById(id)
                .orElseThrow();

        return this.questionsMapper.toModel(questionsExamEntity);
    }
}
