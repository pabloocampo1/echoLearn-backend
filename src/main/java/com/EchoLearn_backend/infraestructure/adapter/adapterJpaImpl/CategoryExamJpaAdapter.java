package com.EchoLearn_backend.infraestructure.adapter.adapterJpaImpl;

import com.EchoLearn_backend.domain.model.Category;
import com.EchoLearn_backend.domain.port.CategoryExamPersistencePort;
import com.EchoLearn_backend.infraestructure.adapter.entity.CategoryExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.mapper.CategoryMapper;
import com.EchoLearn_backend.infraestructure.adapter.repository.CategoryRepository;
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
    public Category save(@Valid Category category) {
        CategoryExamEntity categoryExamEntity = new CategoryExamEntity();
        category.setAvailable(true);

        return this.categoryMapper.toDomain(this.categoryRepository.save(this.categoryMapper.toDbo(category)));
    }
}
