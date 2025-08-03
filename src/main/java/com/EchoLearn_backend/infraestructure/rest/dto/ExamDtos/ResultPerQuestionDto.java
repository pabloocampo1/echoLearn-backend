package com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos;

import lombok.Data;

@Data
public class ResultPerQuestionDto {
    private String question;
    private Boolean isCorrect;
    private String questionSelectForTheUser;
}
