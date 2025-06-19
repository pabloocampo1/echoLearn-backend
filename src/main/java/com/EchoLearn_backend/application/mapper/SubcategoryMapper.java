package com.EchoLearn_backend.application.mapper;

import com.EchoLearn_backend.application.usecases.CategoryUseCase.CategoryUseCase;
import com.EchoLearn_backend.domain.model.Category;
import com.EchoLearn_backend.domain.model.SubCategory;
import com.EchoLearn_backend.infraestructure.rest.dto.SubcategoryDtos.SubcategoryDto;
import com.EchoLearn_backend.infraestructure.rest.dto.SubcategoryDtos.SubcategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubcategoryMapper {
    @Autowired
    private final CategoryUseCase categoryUseCase;

    public SubcategoryMapper(CategoryUseCase categoryUseCase) {
        this.categoryUseCase = categoryUseCase;
    }

    public SubcategoryResponse toResponse(SubCategory subCategory){
        List<Category> categories = this.categoryUseCase.getAllById(subCategory.getCategories());
        List<String> namesCategory = categories.stream().map(Category::getTitle).toList();
        return SubcategoryResponse
                .builder()
                .id_subcategory(subCategory.getId_subcategory())
                .title(subCategory.getTitle())
                .description(subCategory.getDescription())
                .createDate(subCategory.getCreateDate())
                .available(subCategory.getAvailable())
                .categories(namesCategory)
                .imageUrl(subCategory.getImage())
                .build();
    }
// crear metdi o solo para editar
    public SubCategory toDomain (SubcategoryDto subcategoryDto) {
        return SubCategory
                .builder()
                .id_subcategory(subcategoryDto.getId_Subcategory())
                .title(subcategoryDto.getTitle())
                .description(subcategoryDto.getDescription())
                .available(subcategoryDto.getAvailable())
                .categories(subcategoryDto.getId_categories())
                .image(subcategoryDto.getImageUrl())
                .build();
    }
}
