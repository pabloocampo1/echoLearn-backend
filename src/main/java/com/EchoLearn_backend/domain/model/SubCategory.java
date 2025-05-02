package com.EchoLearn_backend.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class SubCategory {
    @NotNull
    private Integer id_category;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private Boolean available;

    private List<TopicExam> topicExams;

}
