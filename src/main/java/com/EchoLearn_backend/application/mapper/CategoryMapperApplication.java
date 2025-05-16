package com.EchoLearn_backend.application.mapper;

import com.EchoLearn_backend.domain.model.Category;
import com.EchoLearn_backend.domain.model.User;
import com.EchoLearn_backend.infraestructure.adapter.entity.CategoryExamEntity;
import com.EchoLearn_backend.infraestructure.rest.dto.category.CategoryDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperApplication {

    public Category toDomain(CategoryDto category){
        return Category
                .builder()
                .id_category(category.getId())
                .title(category.getTitle())
                .description(category.getDescription())
                .available(category.getAvailable())
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
