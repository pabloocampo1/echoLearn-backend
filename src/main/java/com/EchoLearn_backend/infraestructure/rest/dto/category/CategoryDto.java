package com.EchoLearn_backend.infraestructure.rest.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class CategoryDto {

    private Integer id;

    private String title;

    private Boolean available;

    private String description;
    private LocalDateTime createDate;
}
