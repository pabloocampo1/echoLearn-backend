package com.EchoLearn_backend.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ExamModel {
    private Long id_exam;
    @NotBlank
    private  String title;
    private String description;
    @NotBlank
    private String level;
    @NotNull
    private Integer duration;
    @NotNull
    private Integer points;
    @NotBlank
    private Boolean available;

    private List<QuestionModel> questions;

    private List<Integer> subCategories;


}
