package com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExamSubmissionDTO {
    // general data
    @NotNull(message = "El ID del examen no puede ser nulo")
    private Long exam_id;

    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Integer id_user;

    private LocalDateTime dateOfTheExam;

    private Long durationOfTest;

    @NotNull(message = "Las respuestas no pueden ser nulas")
    @Size(min = 1, message = "Debe haber al menos una respuesta")
    private List<AnswerSubmissionDto> answers;
}
