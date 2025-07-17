package com.EchoLearn_backend.infraestructure.adapter.adapterJpaImpl;

import com.EchoLearn_backend.domain.model.Category;
import com.EchoLearn_backend.domain.port.CategoryExamPersistencePort;
import com.EchoLearn_backend.infraestructure.adapter.entity.CategoryExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.mapper.CategoryMapper;
import com.EchoLearn_backend.infraestructure.adapter.projection.CategoryHomeProjection;
import com.EchoLearn_backend.infraestructure.adapter.repository.CategoryRepository;

import com.EchoLearn_backend.infraestructure.rest.dto.category.CategoryHomeDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryExamJpaAdapter  implements CategoryExamPersistencePort {
    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final CategoryMapper categoryMapper;

    public CategoryExamJpaAdapter(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> getAll() {
        List<CategoryExamEntity> categories = (List<CategoryExamEntity>) this.categoryRepository.findAll();
        return categories.stream().map(categoryMapper::toDomain).toList();
    }

    @Override
    public List<Category> findAllById(List<Integer> ids) {
        List<CategoryExamEntity> categoryExamEntities = (List<CategoryExamEntity>) this.categoryRepository.findAllById(ids);

        return categoryExamEntities.stream().map(this.categoryMapper::toDomain).toList();
    }

    @Override
    public List<Category> getAllAvailable() {
        List<CategoryExamEntity> categories = (List<CategoryExamEntity>) this.categoryRepository.findByAvailableTrue();
        return categories
                .stream()
                .map(this.categoryMapper::toDomain)
                .toList();
    }


    @Override
    public void deleteCategory(Integer id) {
        this.categoryRepository.deleteById(id);
    }

    @Override
    public Category save(@Valid Category category) {
        CategoryExamEntity categoryExamEntity = new CategoryExamEntity();
        return this.categoryMapper.toDomain(this.categoryRepository.save(this.categoryMapper.toDbo(category)));
    }


    @Override
    public Category update(Category category){
        CategoryExamEntity newCategory = this.categoryMapper.toDbo(category);
        CategoryExamEntity categoryExamEntityUpdated = this.categoryRepository.save(newCategory);
        return this.categoryMapper.toDomain(categoryExamEntityUpdated);
    }

    @Override
    public Category findById(Integer id) {
        return this.categoryMapper.toDomain(this.categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("category no found")));
    }

    @Override
    public List<Category> findByName(String name) {
        List<CategoryExamEntity> categories = (List<CategoryExamEntity>) this.categoryRepository.findByTitleContainingIgnoreCase(name);
        return categories
                .stream()
                .map(this.categoryMapper::toDomain)
                .toList();
    }

    @Override
    public Boolean existById(Integer id) {
        return this.categoryRepository.existsById(id);
    }

    @Override
    public List<CategoryHomeDto> findAllCategoriesForHome() {
        List<CategoryHomeProjection> homeProjectionList = this.categoryRepository.findAllProjectionsByHome();

        return homeProjectionList.stream().map(
                categoryHomeProjection -> {
                    return CategoryHomeDto
                            .builder()
                            .id(categoryHomeProjection.getId())
                            .title(categoryHomeProjection.getTitle())
                            .description(categoryHomeProjection.getDescription())
                            .available(categoryHomeProjection.getAvailable())
                            .createDate(categoryHomeProjection.getCreateDate())
                            .imageUrl(categoryHomeProjection.getImageUrl())
                            .totalSubcategories(categoryHomeProjection.getTotalSubcategories())
                            .build();
                }
        ).toList();
    }

}
