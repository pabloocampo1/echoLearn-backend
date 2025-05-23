package com.EchoLearn_backend.infraestructure.adapter.mapper;

import com.EchoLearn_backend.domain.model.Category;
import com.EchoLearn_backend.infraestructure.adapter.entity.CategoryExamEntity;
import com.EchoLearn_backend.infraestructure.rest.dto.category.CategoryDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryExamEntity toDbo(Category category){
        return CategoryExamEntity
                .builder()
                .title(category.getTitle())
                .description(category.getDescription())
                .available(category.getAvailable())
                .build();
    }

    public Category toDomain(CategoryExamEntity category){
        return Category
                .builder()
                .id_category(category.getId_category())
                .title(category.getTitle())
                .available(category.getAvailable())
                .description(category.getDescription())
                .build();
    }

    public CategoryDto toResponse(Category category){
        return CategoryDto
                .builder()
                .title(category.getTitle())
                .description(category.getDescription())
                .build();
    }
}
