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
public class SubCategory {
    @NotNull
    private Integer id_subcategory;
    @NotBlank
    private String title;
    @NotBlank
    private String description;

    @NotBlank
    private Boolean available;

    @NotNull
    private List<Integer> categories;

    private LocalDateTime createDate;

    private List<TopicExam> topicExams;

    private String image;

}
