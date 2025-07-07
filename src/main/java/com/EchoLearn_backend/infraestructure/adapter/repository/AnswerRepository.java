package com.EchoLearn_backend.infraestructure.adapter.repository;

import com.EchoLearn_backend.infraestructure.adapter.entity.AnswerExamEntity;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<AnswerExamEntity, Integer> {
}
