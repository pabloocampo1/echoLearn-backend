package com.EchoLearn_backend.infraestructure.adapter.repository;

import com.EchoLearn_backend.infraestructure.adapter.entity.ExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.projection.ExamHomeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExamRepositoryJpa  extends JpaRepository<ExamEntity, Long> {

    @Query(value = """
            SELECT 
                e.id_exam AS id_exam,
                e.title  AS title,
                e.description AS description,
                e.level AS level,
                e.duration AS duration,
                e.points AS points
            FROM exam_entity e
            WHERE e.available = TRUE
            ORDER BY e.points DESC;
            
            """, nativeQuery = true)
    List<ExamHomeProjection>  findAllExamForHome();
}
