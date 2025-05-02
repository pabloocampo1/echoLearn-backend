package com.EchoLearn_backend.application.service.category;

import com.EchoLearn_backend.application.mapper.CategoryMapperApplication;
import com.EchoLearn_backend.application.usecases.CategoryUseCase.CategoryUseCase;
import com.EchoLearn_backend.domain.model.Category;
import com.EchoLearn_backend.domain.model.User;
import com.EchoLearn_backend.domain.port.CategoryExamPersistencePort;
import com.EchoLearn_backend.infraestructure.adapter.entity.CategoryExamEntity;
import com.EchoLearn_backend.infraestructure.rest.dto.category.CategoryDto;
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

    @Transactional
    @Override
    public Category save(@Valid Category category) {
       try{
           return this.categoryExamPersistencePort.save(category);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }


}
