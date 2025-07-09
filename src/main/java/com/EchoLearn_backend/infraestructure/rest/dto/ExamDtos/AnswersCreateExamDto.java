package com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswersCreateExamDto {
    @NotBlank
    private String answerText;
    @NotNull
    private Boolean isCorrect;
}
