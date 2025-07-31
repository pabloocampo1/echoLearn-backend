package com.EchoLearn_backend.infraestructure.adapter.repository;

import com.EchoLearn_backend.infraestructure.adapter.entity.QuestionsExamEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends CrudRepository<QuestionsExamEntity, Integer> {

    @Query(value = "SELECT * FROM question_exam q WHERE q.id_exam = :id_exam", nativeQuery = true)
    List<QuestionsExamEntity> findAllByExamId(@Param("id_exam") Long id_exam);
}
