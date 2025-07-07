package com.EchoLearn_backend.domain.port;

import com.EchoLearn_backend.domain.model.AnswerModel;

public interface AnswerPersistencePort {
    AnswerModel save(AnswerModel answerModel);
}
