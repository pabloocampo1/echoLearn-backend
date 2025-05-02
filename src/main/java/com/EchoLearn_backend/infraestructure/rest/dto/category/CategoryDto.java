package com.EchoLearn_backend.infraestructure.rest.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CategoryDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
