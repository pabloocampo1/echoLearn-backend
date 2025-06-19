package com.EchoLearn_backend.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Category {

    private Integer id_category;

    private String title;

    private String description;

    private Boolean available;

    private LocalDateTime createDate;

    private List<SubCategory> subcategoryExams;

    private String imageUrl;
}
