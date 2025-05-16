package com.EchoLearn_backend.infraestructure.adapter.repository;

import com.EchoLearn_backend.domain.model.Category;
import com.EchoLearn_backend.infraestructure.adapter.entity.CategoryExamEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<CategoryExamEntity, Integer> {
    List<CategoryExamEntity> findByTitleContainingIgnoreCase(String name);
    List<CategoryExamEntity> findByAvailableTrue();
}
