package com.EchoLearn_backend.infraestructure.adapter.repository;

import com.EchoLearn_backend.infraestructure.adapter.entity.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepositoryJpa  extends JpaRepository<ExamEntity, Long> {
}
