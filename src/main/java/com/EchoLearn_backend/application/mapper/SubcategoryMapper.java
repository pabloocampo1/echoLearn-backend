package com.EchoLearn_backend.application.mapper;

import com.EchoLearn_backend.application.usecases.CategoryUseCase.CategoryUseCase;
import com.EchoLearn_backend.application.usecases.SubCategoryUseCase.SubCategoryUseCase;
import com.EchoLearn_backend.domain.model.Category;
import com.EchoLearn_backend.domain.model.SubCategory;
import com.EchoLearn_backend.infraestructure.rest.dto.SubcategoryDtos.SubcategoryDto;
import com.EchoLearn_backend.infraestructure.rest.dto.SubcategoryDtos.SubcategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class SubcategoryMapper {
    private final CategoryUseCase categoryUseCase;

    public SubcategoryMapper(CategoryUseCase categoryUseCase) {
        this.categoryUseCase = categoryUseCase;
    }

    public SubcategoryResponse toResponse(SubCategory subCategory){
        Category category = this.categoryUseCase.findById(subCategory.getCategory());
        return SubcategoryResponse
                .builder()
                .id_subcategory(subCategory.getId_subcategory())
                .title(subCategory.getTitle())
                .description(subCategory.getDescription())
                .createDate(subCategory.getCreateDate())
                .available(subCategory.getAvailable())
                .category(category.getTitle())
                .build();
    }

    public SubCategory toDomain (SubcategoryDto subcategoryDto) {
        return SubCategory
                .builder()
                .id_subcategory(subcategoryDto.getId_Subcategory())
                .title(subcategoryDto.getTitle())
                .description(subcategoryDto.getDescription())
                .available(subcategoryDto.getAvailable())
                .category(subcategoryDto.getId_category())
                .build();
    }
}
