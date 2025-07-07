package com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCreateExamDto {
    private String question;
    private Boolean available;
    //  answers list
    private List<AnswersCreateExamDto> answers;
}
