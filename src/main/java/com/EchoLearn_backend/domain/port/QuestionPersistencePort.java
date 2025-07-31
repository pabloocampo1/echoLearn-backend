package com.EchoLearn_backend.domain.port;

import com.EchoLearn_backend.domain.model.QuestionModel;
import com.EchoLearn_backend.infraestructure.adapter.entity.QuestionsExamEntity;

import java.util.List;

public interface QuestionPersistencePort {
    QuestionModel save(QuestionModel questionModel);
    QuestionModel findById(Integer id);
    List<QuestionModel> findAllByExamId(Long id);
}
