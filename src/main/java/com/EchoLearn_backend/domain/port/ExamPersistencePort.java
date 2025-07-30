package com.EchoLearn_backend.domain.port;

import com.EchoLearn_backend.domain.model.ExamModel;
import com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos.ExamHomeDto;

import java.util.List;

public interface ExamPersistencePort {
    ExamModel save(ExamModel examModel);
    ExamModel getById(Long id);
    List<ExamModel> getAll();
    List<ExamHomeDto> getAllExamForHome();
    List<ExamHomeDto> findAllBySubcategory(Integer id);
}
