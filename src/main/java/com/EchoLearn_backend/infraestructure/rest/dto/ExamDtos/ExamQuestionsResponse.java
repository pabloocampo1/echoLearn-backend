package com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos;

import lombok.Data;

import java.util.List;

@Data
public class ExamQuestionsResponse {
    private Long exam_id;
    private List<QuestionResponse> questionResponseList;
}
