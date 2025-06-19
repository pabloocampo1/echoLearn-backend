package com.EchoLearn_backend.infraestructure.rest.controler;

import com.EchoLearn_backend.application.mapper.SubcategoryMapper;
import com.EchoLearn_backend.application.service.CloudinaryService;
import com.EchoLearn_backend.application.usecases.SubCategoryUseCase.SubCategoryUseCase;
import com.EchoLearn_backend.domain.model.SubCategory;
import com.EchoLearn_backend.infraestructure.rest.dto.SubcategoryDtos.SubcategoryDto;
import com.EchoLearn_backend.infraestructure.rest.dto.SubcategoryDtos.SubcategoryResponse;
import com.EchoLearn_backend.infraestructure.rest.dto.category.CategoryDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/subcategory")
public class SubcategoryController {
    private final SubCategoryUseCase subCategoryUseCase;
    private final SubcategoryMapper subcategoryMapper;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public SubcategoryController(SubCategoryUseCase subCategoryUseCase, SubcategoryMapper subcategoryMapper, CloudinaryService cloudinaryService) {
        this.subCategoryUseCase = subCategoryUseCase;
        this.subcategoryMapper = subcategoryMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<SubcategoryResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int elements){
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

    @GetMapping("/findByTitle/{title}")
    public ResponseEntity<List<SubcategoryResponse>> findAllByTitle(@Valid @PathVariable("title") String title) {
        List<SubCategory> subCategoryList = this.subCategoryUseCase.getByTitle(title);
        List<SubcategoryResponse> subcategoryResponseList = subCategoryList.stream().map( this.subcategoryMapper::toResponse).toList();
        return new ResponseEntity<>(subcategoryResponseList, HttpStatus.OK);

    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SubcategoryResponse> save(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = true) String title,
            @RequestParam(required = true) String description,
            @RequestParam Boolean available,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(value = "categories") List<String> categories

    ) {

        String imageUrl = null;
        try {
           if(image != null) {
               imageUrl = this.cloudinaryService.uploadImage(image);
           }
            List<Integer> categoriesListInteger = categories.stream()
                    .map(Integer::valueOf)
                    .toList();

            SubcategoryDto subcategoryDto = new SubcategoryDto();
            subcategoryDto.setTitle(title);
            subcategoryDto.setDescription(description);
            subcategoryDto.setAvailable(available);
            subcategoryDto.setId_categories(categoriesListInteger);
            subcategoryDto.setImageUrl(imageUrl);
            System.out.println(subcategoryDto);

            SubCategory subCategoryDomain = this.subcategoryMapper.toDomain(subcategoryDto);
            SubcategoryResponse subcategoryResponse = this.subcategoryMapper.toResponse(
                    this.subCategoryUseCase.save(subCategoryDomain)
            );


            return new ResponseEntity<>(subcategoryResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            if (imageUrl == null) {
                this.cloudinaryService.deleteFile(imageUrl);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SubcategoryResponse> update(
            @RequestParam(required = true) Integer id,
            @RequestParam(required = true) String title,
            @RequestParam(required = true) String description,
            @RequestParam Boolean available,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(value = "categories") List<String> categories
    ) {
        String imageUrl = null;
        try {
            if(image != null) {
                imageUrl = this.cloudinaryService.uploadImage(image);
            }
            List<Integer> categoriesListInteger = categories.stream()
                    .map(Integer::valueOf)
                    .toList();

            SubcategoryDto subcategoryDto = new SubcategoryDto();
            subcategoryDto.setId_Subcategory(id);
            subcategoryDto.setTitle(title);
            subcategoryDto.setDescription(description);
            subcategoryDto.setAvailable(available);
            subcategoryDto.setId_categories(categoriesListInteger);
            subcategoryDto.setImageUrl(imageUrl);
            SubCategory subCategoryDomain = this.subcategoryMapper.toDomain(subcategoryDto);
            SubcategoryResponse subcategoryResponse = this.subcategoryMapper.toResponse(
                    this.subCategoryUseCase.update(subCategoryDomain)
            );

            return new ResponseEntity<>(subcategoryResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();

                this.cloudinaryService.deleteFile(imageUrl);

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
     }

     @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSubCategory(@PathVariable("id") Integer id) {
         this.subCategoryUseCase.delete(id);
        return new ResponseEntity<>( HttpStatus.OK);
     }












}
