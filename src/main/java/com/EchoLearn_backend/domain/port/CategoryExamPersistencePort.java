package com.EchoLearn_backend.domain.port;

import com.EchoLearn_backend.domain.model.Category;
import com.EchoLearn_backend.infraestructure.adapter.entity.CategoryExamEntity;

import java.util.List;

public interface CategoryExamPersistencePort {
    List<Category> getAll();
    Category save(Category category);
}
