package com.EchoLearn_backend.infraestructure.adapter.repository;

import com.EchoLearn_backend.infraestructure.adapter.entity.CategoryExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.entity.SubCategoryExamEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface SubcategoryRepository extends JpaRepository<SubCategoryExamEntity, Integer> {
 //   @Query("select * from subcategory_exam from",nativeQuery = true )
  //  Page<SubCategoryExamEntity> findByAvailableTrueAndCategory(@PathVariable("id") Integer id, Pageable pageable);
 List<SubCategoryExamEntity> findAllByTitleContainingIgnoreCase(String title);
}
