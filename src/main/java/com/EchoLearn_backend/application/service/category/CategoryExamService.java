package com.EchoLearn_backend.application.service.category;

import com.EchoLearn_backend.application.mapper.CategoryMapperApplication;
import com.EchoLearn_backend.application.usecases.CategoryUseCase.CategoryUseCase;
import com.EchoLearn_backend.domain.model.Category;
import com.EchoLearn_backend.domain.port.CategoryExamPersistencePort;
import com.EchoLearn_backend.infraestructure.rest.dto.category.CategoryDto;
import com.EchoLearn_backend.infraestructure.rest.dto.category.CategoryHomeDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CategoryExamService implements CategoryUseCase {

    @Autowired
    private final CategoryExamPersistencePort categoryExamPersistencePort;

    @Autowired
    private final CategoryMapperApplication categoryMapperApplication;

    public CategoryExamService(CategoryExamPersistencePort categoryExamPersistencePort, CategoryMapperApplication categoryMapperApplication) {
        this.categoryExamPersistencePort = categoryExamPersistencePort;
        this.categoryMapperApplication = categoryMapperApplication;
    }

    @Override
    public List<Category> getAll() {
        return this.categoryExamPersistencePort.getAll();
    }

    @Override
    public List<Category> getAllById(List<Integer> ids) {
        return this.categoryExamPersistencePort.findAllById(ids);
    }

    @Override
    public List<Category> getAllAvailable() {
        return this.categoryExamPersistencePort.getAllAvailable();
    }

    @Transactional
    @Override
    public Category save(@Valid Category category) {
       try{
           return this.categoryExamPersistencePort.save(category);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }

    @Override
    public Category update(CategoryDto category) {
        Category category1 = this.categoryMapperApplication.toDomain(category);
        return this.categoryExamPersistencePort.update(category1);
    }

    @Override
    @Transactional
    public void deleteCategory(Integer id) {
        if (id == null || id <0){
            throw new IllegalArgumentException("not id");
        }
        this.categoryExamPersistencePort.deleteCategory(id);
    }

    @Override
    public Category findById(Integer id) {
        return this.categoryExamPersistencePort.findById(id);
    }

    @Override
    public List<Category> findByName(String name) {
        return this.categoryExamPersistencePort.findByName(name);
    }

    @Override
    public List<CategoryHomeDto> getAllCategoriesAvailableForHome() {
        return this.categoryExamPersistencePort.findAllCategoriesForHome();
    }

}
