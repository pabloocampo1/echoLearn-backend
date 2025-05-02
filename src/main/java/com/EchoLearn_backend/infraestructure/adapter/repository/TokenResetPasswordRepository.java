package com.EchoLearn_backend.infraestructure.adapter.repository;

import com.EchoLearn_backend.infraestructure.adapter.entity.TokenResetPassword;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Date;

public interface TokenResetPasswordRepository extends CrudRepository<TokenResetPassword, Integer > {
    Boolean existsByToken(Integer token);
    void deleteByExpiredTimeBefore(LocalDateTime date);
    Boolean existsByEmail(String email);
}
