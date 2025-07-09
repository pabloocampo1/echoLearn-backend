package com.EchoLearn_backend.infraestructure.rest.controler;

import com.EchoLearn_backend.application.usecases.ExamUseCase;
import com.EchoLearn_backend.domain.model.ExamModel;
import com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos.ExamCreateDto;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam")
public class ExamController {
    private final ExamUseCase examUseCase;

    public ExamController(ExamUseCase examUseCase) {
        this.examUseCase = examUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createExam(@RequestBody ExamCreateDto examCreateDto) {
       try {
          return new ResponseEntity<>(HttpStatus.CREATED) ;
       } catch (Exception e) {
           e.printStackTrace();
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }

    }


    // GET METHODS
    @GetMapping("/geAll")
    public ResponseEntity<List<ExamModel>> getAll(){
        return new ResponseEntity<>(this.examUseCase.getAll(), HttpStatus.OK);
    }

    @GetMapping("/getAll/home")
    public ResponseEntity<List<ExamModel>> getAllExamForHome(){
        return new ResponseEntity<>(this.examUseCase.getAll(), HttpStatus.OK);
    }
}
