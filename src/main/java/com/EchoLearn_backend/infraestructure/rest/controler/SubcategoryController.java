package com.EchoLearn_backend.infraestructure.rest.controler;

import com.EchoLearn_backend.application.mapper.SubcategoryMapper;
import com.EchoLearn_backend.application.usecases.SubCategoryUseCase.SubCategoryUseCase;
import com.EchoLearn_backend.domain.model.SubCategory;
import com.EchoLearn_backend.infraestructure.rest.dto.SubcategoryDtos.SubcategoryDto;
import com.EchoLearn_backend.infraestructure.rest.dto.SubcategoryDtos.SubcategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subcategory")
public class SubcategoryController {
    private final SubCategoryUseCase subCategoryUseCase;
    private final SubcategoryMapper subcategoryMapper;

    @Autowired
    public SubcategoryController(SubCategoryUseCase subCategoryUseCase, SubcategoryMapper subcategoryMapper) {
        this.subCategoryUseCase = subCategoryUseCase;
        this.subcategoryMapper = subcategoryMapper;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<SubcategoryResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int elements){
        try{
            Pageable pageableRequest = PageRequest.of(page, elements);
            Page<SubCategory> categories = this.subCategoryUseCase.getAll(pageableRequest);
            return new ResponseEntity<>(
                    categories.map(this.subcategoryMapper::toResponse),HttpStatus.OK
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<SubcategoryResponse> save(@RequestBody SubcategoryDto subcategoryDto) {
        System.out.println(subcategoryDto.getId_category());
        try{
            SubCategory subCategoryDomain = this.subcategoryMapper.toDomain(subcategoryDto);
            SubcategoryResponse subcategoryResponse = this.subcategoryMapper.toResponse(
                    this.subCategoryUseCase.save(subCategoryDomain)
            );
            return new ResponseEntity<>(subcategoryResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




}
