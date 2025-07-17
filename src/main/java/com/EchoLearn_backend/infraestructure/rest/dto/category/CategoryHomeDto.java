package com.EchoLearn_backend.infraestructure.rest.dto.category;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryHomeDto {
    private Integer id;
    private String title;
    private Boolean available;
    private String description;
    private LocalDateTime createDate;
    private String imageUrl;
    private Integer totalSubcategories;
}
