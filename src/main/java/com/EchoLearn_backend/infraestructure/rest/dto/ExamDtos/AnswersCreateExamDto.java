package com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswersCreateExamDto {
    private String answerText;
    private Boolean isCorrect;
}
