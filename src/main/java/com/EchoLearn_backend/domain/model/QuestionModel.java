package com.EchoLearn_backend.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionModel {
    private Integer id_question;
    private Integer exam_id;
    private String question;
    private Boolean available;
    private String type;
    private List<AnswerModel> answerModels;
}
