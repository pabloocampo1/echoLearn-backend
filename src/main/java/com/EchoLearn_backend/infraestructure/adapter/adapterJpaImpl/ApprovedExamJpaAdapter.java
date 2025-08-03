package com.EchoLearn_backend.infraestructure.adapter.adapterJpaImpl;

import com.EchoLearn_backend.domain.model.ApprovedExamModel;
import com.EchoLearn_backend.domain.port.ApprovedExamPersistencePort;
import com.EchoLearn_backend.infraestructure.adapter.entity.ApprovedExam;
import com.EchoLearn_backend.infraestructure.adapter.repository.ApprovedExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApprovedExamJpaAdapter implements ApprovedExamPersistencePort {

    @Autowired
    private final ApprovedExamRepository approvedExamRepository;

    public ApprovedExamJpaAdapter(ApprovedExamRepository approvedExamRepository) {
        this.approvedExamRepository = approvedExamRepository;
    }


    @Override
    public List<ApprovedExamModel> findAllByUserId(Integer user_id) {
        return List.of();
    }

    @Override
    public ApprovedExam save(ApprovedExam approvedExam) {

        return this.approvedExamRepository.save(approvedExam);
    }
}
