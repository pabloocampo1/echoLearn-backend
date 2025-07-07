package com.EchoLearn_backend.infraestructure.adapter.adapterJpaImpl;

import com.EchoLearn_backend.domain.model.AnswerModel;
import com.EchoLearn_backend.domain.port.AnswerPersistencePort;
import com.EchoLearn_backend.infraestructure.adapter.entity.AnswerExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.mapper.AnswerMapper;
import com.EchoLearn_backend.infraestructure.adapter.mapper.QuestionsMapper;
import com.EchoLearn_backend.infraestructure.adapter.repository.AnswerRepository;
import com.EchoLearn_backend.infraestructure.adapter.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerJpaAdpater implements AnswerPersistencePort {
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    @Autowired
    public AnswerJpaAdpater(AnswerRepository answerRepository, AnswerMapper answerMapper) {
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
    }


    @Override
    public AnswerModel save(AnswerModel answerModel) {
        AnswerExamEntity answerExamEntity = this.answerMapper.toDbo(answerModel);
        AnswerExamEntity answerExamEntitySaved = this.answerRepository.save(answerExamEntity);
        return this.answerMapper.toModel(answerExamEntitySaved);
    }
}
