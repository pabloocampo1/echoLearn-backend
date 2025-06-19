package com.EchoLearn_backend.infraestructure.rest.dto.SubcategoryDtos;


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
public class SubcategoryResponse {

    private Integer id_subcategory;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private Boolean available;

    private List<String> categories;

    private LocalDateTime createDate;

    private String imageUrl;


}
