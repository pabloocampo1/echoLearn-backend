package com.EchoLearn_backend.infraestructure.adapter.repository;

import com.EchoLearn_backend.infraestructure.adapter.entity.CategoryExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.entity.SubCategoryExamEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface SubcategoryRepository extends JpaRepository<SubCategoryExamEntity, Integer> {
    List<SubCategoryExamEntity> findAllByTitleContainingIgnoreCase(String title);

    @Query("""
    SELECT s FROM SubCategoryExamEntity s
    JOIN s.categories c
    WHERE s.available = true AND c.id_category = :categoryId
""")
    Page<SubCategoryExamEntity> findAvailableByCategoryId(@Param("categoryId") Integer categoryId, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM exam_subcategory WHERE id_subcategory = :id", nativeQuery = true)
    void deleteRelationshipsWithExams(@Param("id") Integer id_subcategory);
}
