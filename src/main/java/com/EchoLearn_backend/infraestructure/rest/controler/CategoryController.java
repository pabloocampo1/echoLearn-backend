package com.EchoLearn_backend.infraestructure.rest.controler;

import com.EchoLearn_backend.application.mapper.CategoryMapperApplication;
import com.EchoLearn_backend.application.service.CloudinaryService;
import com.EchoLearn_backend.application.usecases.CategoryUseCase.CategoryUseCase;

import com.EchoLearn_backend.domain.model.Category;
import com.EchoLearn_backend.infraestructure.adapter.mapper.CategoryMapper;
import com.EchoLearn_backend.infraestructure.rest.dto.category.CategoryDto;
import com.cloudinary.Cloudinary;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private final CloudinaryService cloudinaryService;


    public CategoryController(CategoryUseCase useCase, CategoryMapper categoryMapper, CategoryMapperApplication categoryMapperApplication, CloudinaryService cloudinaryService) {
        this.useCase = useCase;

        this.categoryMapper = categoryMapper;
        this.categoryMapperApplication = categoryMapperApplication;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll() {
        return new ResponseEntity<>(this.useCase.getAll().stream().map(categoryMapper::toResponse).toList(), HttpStatus.OK);
    }

    @GetMapping("/getAllAvailable")
    public ResponseEntity<List<CategoryDto>> getAllAvailable() {
        List<Category> categories = this.useCase.getAllAvailable();
        return new ResponseEntity<>(
                categories.stream().map(this.categoryMapper::toResponse).toList()
                , HttpStatus.OK);
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<List<CategoryDto>> getByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(
                this.useCase.findByName(name)
                        .stream()
                        .map(this.categoryMapper::toResponse)
                        .toList(),
                HttpStatus.OK
        );

    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable("id") Integer id) {
        try {
            return new ResponseEntity<>(this.categoryMapper.toResponse(this.useCase.findById(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<CategoryDto> update(@RequestBody CategoryDto categoryDto) {
        try {
            return new ResponseEntity<>(this.categoryMapper.toResponse(this.useCase.update(categoryDto)), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryDto> save(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = true) String title,
            @RequestParam(required = true) String description,
            @RequestParam Boolean available,
            @RequestParam(required = false) MultipartFile image
    ) {
        String imageUrl = null;
        try {
            if(image != null){
                imageUrl = this.cloudinaryService.uploadImage(image);
            }
            CategoryDto dto = CategoryDto
                    .builder()
                    .id(id)
                    .title(title)
                    .description(description)
                    .available(available)
                    .imageUrl(imageUrl)
                    .build();

            return new ResponseEntity<>(
                    this.categoryMapper.toResponse(this.useCase.save(this.categoryMapperApplication.toDomain(dto))
                    ), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            this.cloudinaryService.deleteFile(imageUrl);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        try {
            this.useCase.deleteCategory(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}



