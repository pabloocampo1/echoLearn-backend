package com.EchoLearn_backend.infraestructure.rest.controler;

import com.EchoLearn_backend.Exception.BadRequestException;
import com.EchoLearn_backend.application.usecases.ExamUseCase;
import com.EchoLearn_backend.domain.model.ExamModel;
import com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos.ExamCreateDto;
import com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos.ExamHomeDto;
import jakarta.validation.Valid;
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
    public ResponseEntity<ExamModel> createExam(@RequestBody ExamCreateDto examCreateDto) {

      try {
          return new ResponseEntity<>(this.examUseCase.saveExam(examCreateDto),HttpStatus.CREATED) ;
       }catch (BadRequestException e) {
          throw e;
      }  catch (Exception e) {
           e.printStackTrace();
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<ExamModel>> getAll(){
       try{
           return new ResponseEntity<>(this.examUseCase.getAll(), HttpStatus.OK);
       }catch (Exception e) {
           e.printStackTrace();
           throw new RuntimeException(e);
       }
    }

    @GetMapping("/getAll/home")
    public ResponseEntity<List<ExamHomeDto>> getAllExamForHome(){
        return new ResponseEntity<>(this.examUseCase.getAllExamForHome(), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ExamModel> getById(@PathVariable("id") @Valid Long id ){
      try{
          return new ResponseEntity<>(this.examUseCase.getById(id), HttpStatus.OK);
      } catch (Exception e) {
          e.printStackTrace();
          throw new RuntimeException(e);
      }
    }

    @GetMapping(path = "/getBySubcategory/{id_subcategory}")
    public ResponseEntity<List<ExamHomeDto>> getAllBySubcategory(@PathVariable("id_subcategory") @Valid Integer id) {
        return new ResponseEntity<>(this.examUseCase.getAllExamBySubcategory(id), HttpStatus.OK);
    }


}
