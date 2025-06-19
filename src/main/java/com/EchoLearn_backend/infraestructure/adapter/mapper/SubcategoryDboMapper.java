package com.EchoLearn_backend.infraestructure.adapter.mapper;

import com.EchoLearn_backend.domain.model.SubCategory;
import com.EchoLearn_backend.domain.port.CategoryExamPersistencePort;
import com.EchoLearn_backend.infraestructure.adapter.entity.CategoryExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.entity.SubCategoryExamEntity;
import com.EchoLearn_backend.infraestructure.rest.dto.SubcategoryDtos.SubcategoryDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubcategoryDboMapper {
    @Autowired
    private CategoryExamPersistencePort categoryExamPersistencePort;

    public SubCategory toDomain(SubCategoryExamEntity subCategoryExamEntity){
        List<Integer> ids = subCategoryExamEntity.getCategories()
                .stream().map(CategoryExamEntity::getId_category).toList();
        return SubCategory.
                builder()
                .id_subcategory(subCategoryExamEntity.getId_subcategory())
                .title(subCategoryExamEntity.getTitle())
                .description(subCategoryExamEntity.getDescription())
                .available(subCategoryExamEntity.getAvailable())
                .categories(ids)
                .createDate(subCategoryExamEntity.getCreateDate())
                .image(subCategoryExamEntity.getImageUrl())
                .build();
    }

    public SubCategoryExamEntity toDbo(SubCategory subCategory){
        return SubCategoryExamEntity
                .builder()
                .id_subcategory(subCategory.getId_subcategory())
                .title(subCategory.getTitle())
                .description(subCategory.getDescription())
                .available(subCategory.getAvailable())
                .imageUrl(subCategory.getImage())
                .build();
    }
}
