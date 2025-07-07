package com.EchoLearn_backend.domain.model;

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
    private String answerText;
    private Boolean isCorrect;
    private Integer id_question;
}
