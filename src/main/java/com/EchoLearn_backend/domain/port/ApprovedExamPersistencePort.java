package com.EchoLearn_backend.domain.port;

import com.EchoLearn_backend.domain.model.ApprovedExamModel;
import com.EchoLearn_backend.infraestructure.adapter.entity.ApprovedExam;

import java.util.List;

public interface ApprovedExamPersistencePort {
    List<ApprovedExamModel> findAllByUserId(Integer user_id);
    ApprovedExam save(ApprovedExam approvedExam);

}
