package com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos;

import lombok.Data;

import java.util.List;

@Data
public class QuestionResponse {
    private Integer question_id;
    private String questionText;
    private String type;
    private List<AnswerResponse> answerResponseList;
}
