package com.EchoLearn_backend.infraestructure.rest.controler;

import com.EchoLearn_backend.application.usecases.ExamUseCase;
import com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos.ExamCreateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exam")
public class ExamController {
    private final ExamUseCase examUseCase;

    public ExamController(ExamUseCase examUseCase) {
        this.examUseCase = examUseCase;
    }

    // steps to create that functionally:

    // 1. recirbir correctamente el dto. listo
    // en el service de exam, crear un examen con atributos basips, ya uqe necesitos la clave primaria - listo / pero se necesita valdacion por falta de subcategorias
    // con esa clave primaria o esa entidad creada de exam, podemos crear las otras ategorias de
    @PostMapping("/create")
    public ResponseEntity<ExamCreateDto> createExam(@RequestBody ExamCreateDto examCreateDto) {
        System.out.println("ni llega aca");
       try {
           this.examUseCase.saveExam(examCreateDto);
       } catch (Exception e) {
           e.printStackTrace();
           throw new RuntimeException(e);
       }
        return ResponseEntity.ok(examCreateDto);
    }
}
