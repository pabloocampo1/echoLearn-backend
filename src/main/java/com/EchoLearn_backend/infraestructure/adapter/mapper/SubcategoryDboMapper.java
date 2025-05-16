package com.EchoLearn_backend.infraestructure.adapter.mapper;

import com.EchoLearn_backend.domain.model.SubCategory;
import com.EchoLearn_backend.infraestructure.adapter.entity.SubCategoryExamEntity;
import com.EchoLearn_backend.infraestructure.rest.dto.SubcategoryDtos.SubcategoryDto;
import com.EchoLearn_backend.infraestructure.rest.dto.SubcategoryDtos.SubcategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class SubcategoryDboMapper {

    public SubCategory toDomain(SubCategoryExamEntity subCategoryExamEntity){
        return SubCategory.
                builder()
                .id_subcategory(subCategoryExamEntity.getId_subcategory())
                .title(subCategoryExamEntity.getTitle())
                .description(subCategoryExamEntity.getDescription())
                .available(subCategoryExamEntity.getAvailable())
                .category(subCategoryExamEntity.getCategory().getId_category())
                .createDate(subCategoryExamEntity.getCreateDate())
                .build();
    }

    public SubCategoryExamEntity toDbo(SubCategory subCategory){
        return SubCategoryExamEntity
                .builder()
                .id_subcategory(subCategory.getId_subcategory())
                .title(subCategory.getTitle())
                .description(subCategory.getDescription())
                .available(subCategory.getAvailable())
                .build();
    }
}
