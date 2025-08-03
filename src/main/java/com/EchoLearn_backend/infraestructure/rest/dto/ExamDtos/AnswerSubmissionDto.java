package com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnswerSubmissionDto {
    @NotNull
    private Integer id_question;

    @NotBlank
    private String answerSelect;
}
