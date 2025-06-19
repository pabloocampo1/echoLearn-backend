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
                .id_category(category.getId_category())
                .title(category.getTitle())
                .description(category.getDescription())
                .available(category.getAvailable())
                .imageUrl(category.getImageUrl())
                .build();
    }

    public CategoryExamEntity RequestToDbo(CategoryDto category){
        return CategoryExamEntity
                .builder()
                .id_category(category.getId())
                .title(category.getTitle())
                .description(category.getDescription())
                .available(category.getAvailable())
                .imageUrl(category.getImageUrl())
                .build();
    }

    public Category toDomain(CategoryExamEntity category){
        return Category
                .builder()
                .id_category(category.getId_category())
                .title(category.getTitle())
                .available(category.getAvailable())
                .description(category.getDescription())
                .imageUrl(category.getImageUrl())
                .build();
    }

    public CategoryDto toResponse(Category category){
        return CategoryDto
                .builder()
                .id(category.getId_category())
                .title(category.getTitle())
                .description(category.getDescription())
                .available(category.getAvailable())
                .createDate(category.getCreateDate())
                .imageUrl(category.getImageUrl())
                .build();
    }


}
