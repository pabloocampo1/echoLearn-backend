package com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExamResult {
    private Integer userId;
    private String nameUser;

    private String examTitle;
    private Long examId;
    private Double resultExam;
    private Integer numberOfQuestionCorrect;
    private Integer NumberOfQuestionIncorrect;
    private Integer points;
    private Boolean isApproved;
    private LocalDateTime date;
    private Integer minimumPassingScore;
    private Long durationOfTime;
    private List<ResultPerQuestionDto> resultOfQuestions;
}
