package com.EchoLearn_backend.infraestructure.rest.dto.SubcategoryDtos;

import com.EchoLearn_backend.domain.model.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubcategoryDto {
    private Integer id_Subcategory;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private Boolean available;

    @NotBlank
    private List<Integer> id_categories;

    private String imageUrl;

}
