package com.EchoLearn_backend.application.usecases.CategoryUseCase;

import com.EchoLearn_backend.domain.model.Category;
import com.EchoLearn_backend.domain.model.User;
import com.EchoLearn_backend.infraestructure.adapter.entity.CategoryExamEntity;

import java.util.List;

public interface CategoryUseCase {
    List<Category> getAll();
    Category save(Category user);
}
