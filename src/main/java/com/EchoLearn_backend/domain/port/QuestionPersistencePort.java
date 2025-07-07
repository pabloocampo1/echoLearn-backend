package com.EchoLearn_backend.domain.port;

import com.EchoLearn_backend.domain.model.QuestionModel;

public interface QuestionPersistencePort {
    QuestionModel save(QuestionModel questionModel);
    QuestionModel findById(Integer id);
}
