package com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos;

import lombok.Data;

@Data
public class AnswerResponse {
    private Integer answer_id;
    private String answerText;
}
