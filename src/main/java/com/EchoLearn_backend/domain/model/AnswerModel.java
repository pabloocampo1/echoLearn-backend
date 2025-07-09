package com.EchoLearn_backend.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerModel {
    private Integer id_answer;
    @NotBlank
    private String answerText;
    @NotBlank
    private Boolean isCorrect;
    @NotNull
    private Integer id_question;
}
