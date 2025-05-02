package com.EchoLearn_backend.infraestructure.rest.controler;

import com.EchoLearn_backend.application.mapper.CategoryMapperApplication;
import com.EchoLearn_backend.application.usecases.CategoryUseCase.CategoryUseCase;

import com.EchoLearn_backend.infraestructure.adapter.mapper.CategoryMapper;
import com.EchoLearn_backend.infraestructure.rest.dto.category.CategoryDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private final CategoryUseCase useCase;

    @Autowired
    private final CategoryMapper categoryMapper;
    @Autowired
    private final CategoryMapperApplication categoryMapperApplication;


    public CategoryController(CategoryUseCase useCase, CategoryMapper categoryMapper, CategoryMapperApplication categoryMapperApplication) {
        this.useCase = useCase;

        this.categoryMapper = categoryMapper;
        this.categoryMapperApplication = categoryMapperApplication;
    }

    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getAll(){
        return new ResponseEntity<>( this.useCase.getAll().stream().map(categoryMapper::toResponse).toList(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> save(@Valid @RequestBody CategoryDto categoryDto){
        try{
            return new ResponseEntity<>(
                    this.categoryMapper.toResponse(this.useCase.save(this.categoryMapperApplication.toDomain(categoryDto))
                    ), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
