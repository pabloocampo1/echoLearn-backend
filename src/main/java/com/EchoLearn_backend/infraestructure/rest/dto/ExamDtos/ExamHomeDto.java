package com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamHomeDto {
    private Long id_exam;
    private String title;
    private String description;
    private String level;
    private Integer duration;
    private Integer points;
    private Integer totalQuestion;
}
