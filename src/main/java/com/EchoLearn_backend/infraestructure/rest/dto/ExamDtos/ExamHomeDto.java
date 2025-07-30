package com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ExamHomeDto {
    private Long id_exam;
    private String title;
    private String description;
    private String level;
    private Integer duration;
    private Integer points;
    private Integer totalQuestion;

    public ExamHomeDto(Long id_exam, String title, String description, String level, Integer duration, Integer points, Integer totalQuestion) {
        this.id_exam = id_exam;
        this.title = title;
        this.description = description;
        this.level = level;
        this.duration = duration;
        this.points = points;
        this.totalQuestion = totalQuestion;
    }
}
