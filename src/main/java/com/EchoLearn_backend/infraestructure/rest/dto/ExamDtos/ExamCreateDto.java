package com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamCreateDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    
    @NotBlank
    private String level;
    @NotNull
    private  Integer duration;
    @NotNull
    private Integer points;
    @NotNull
    private Boolean available;

    //list of question
    private List<QuestionCreateExamDto> questions;

    private List<Integer> subcategories;
}
