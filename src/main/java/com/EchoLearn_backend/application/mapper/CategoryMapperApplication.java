package com.EchoLearn_backend.application.mapper;

import com.EchoLearn_backend.domain.model.Category;
import com.EchoLearn_backend.domain.model.User;
import com.EchoLearn_backend.infraestructure.adapter.entity.CategoryExamEntity;
import com.EchoLearn_backend.infraestructure.rest.dto.category.CategoryDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperApplication {
    public CategoryDto toResponse(Category category){
        return CategoryDto
                .builder()
                .title(category.getTitle())
                .description(category.getDescription())
                .build();
    }
    public Category toDomain(CategoryDto category){
        return Category
                .builder()
                .title(category.getTitle())
                .description(category.getDescription())
                .build();
    }

    public CategoryExamEntity toDbo(CategoryDto category){
        return CategoryExamEntity
                .builder()
                .title(category.getTitle())
                .description(category.getDescription())
                .build();
    }
}
