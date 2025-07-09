package com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCreateExamDto {
    @NotBlank
    private String question;
    @NotNull
    private Boolean available;
    //  answers list
    private List<AnswersCreateExamDto> answers;
}
