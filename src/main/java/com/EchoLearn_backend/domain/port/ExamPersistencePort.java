package com.EchoLearn_backend.domain.port;

import com.EchoLearn_backend.domain.model.ExamModel;

public interface ExamPersistencePort {
    ExamModel save(ExamModel examModel);
    ExamModel getById(Long id);
}
