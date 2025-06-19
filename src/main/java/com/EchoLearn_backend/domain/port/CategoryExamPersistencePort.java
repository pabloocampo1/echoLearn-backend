package com.EchoLearn_backend.domain.port;

import com.EchoLearn_backend.domain.model.Category;
import com.EchoLearn_backend.infraestructure.adapter.entity.CategoryExamEntity;

import java.util.List;

public interface CategoryExamPersistencePort {
    List<Category> getAll();
    List<Category> findAllById(List<Integer> ids);
    List<Category> getAllAvailable();
    void deleteCategory(Integer id);
    Category save(Category category);
    Category update (Category category);
    Category findById(Integer id);
    List<Category> findByName(String name);
    Boolean existById(Integer id);
}
