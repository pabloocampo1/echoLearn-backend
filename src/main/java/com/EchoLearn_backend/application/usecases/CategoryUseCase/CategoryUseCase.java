package com.EchoLearn_backend.application.usecases.CategoryUseCase;

import com.EchoLearn_backend.domain.model.Category;

import com.EchoLearn_backend.infraestructure.rest.dto.category.CategoryDto;
import com.EchoLearn_backend.infraestructure.rest.dto.category.CategoryHomeDto;

import java.util.List;

public interface CategoryUseCase {
    List<Category> getAll();
    List<Category> getAllById(List<Integer> ids);
    List<Category> getAllAvailable();
    Category save(Category user);
    Category update(CategoryDto category);
    void deleteCategory(Integer id);
    Category findById(Integer id);
    List<Category> findByName(String name);
    List<CategoryHomeDto> getAllCategoriesAvailableForHome();

}
