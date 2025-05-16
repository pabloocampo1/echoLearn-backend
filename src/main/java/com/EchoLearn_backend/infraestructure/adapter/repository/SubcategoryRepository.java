package com.EchoLearn_backend.infraestructure.adapter.repository;

import com.EchoLearn_backend.infraestructure.adapter.entity.CategoryExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.entity.SubCategoryExamEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubcategoryRepository extends JpaRepository<SubCategoryExamEntity, Integer> {
    Page<SubCategoryExamEntity> findByAvailableTrueAndCategory(CategoryExamEntity categoryExamEntity, Pageable pageable);
}
