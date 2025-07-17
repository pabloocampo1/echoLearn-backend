package com.EchoLearn_backend.infraestructure.adapter.repository;

import com.EchoLearn_backend.domain.model.Category;
import com.EchoLearn_backend.infraestructure.adapter.entity.CategoryExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.projection.CategoryHomeProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<CategoryExamEntity, Integer> {
    List<CategoryExamEntity> findByTitleContainingIgnoreCase(String name);
    List<CategoryExamEntity> findByAvailableTrue();

    @Query(value = """
             SELECT
                    COUNT(s.id_subcategory) AS totalSubcategories,
                    c.id_category AS id,
                    c.title AS title,
                    c.available AS available,
                    c.description AS description,
                    c.create_date AS createDate,
                    c.image_url AS imageUrl
                FROM category_exam c
                    LEFT JOIN category_subcategory i ON i.id_category = c.id_category
                    LEFT JOIN subcategory_exam s ON i.id_subcategory = s.id_subcategory
                WHERE 
                    c.available = TRUE
                GROUP BY 
                    c.id_category, c.title, c.available, c.description, c.create_date, c.image_url
            """, nativeQuery = true)
    List<CategoryHomeProjection> findAllProjectionsByHome();


}
