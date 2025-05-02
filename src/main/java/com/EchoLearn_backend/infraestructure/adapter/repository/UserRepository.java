package com.EchoLearn_backend.infraestructure.adapter.repository;

import com.EchoLearn_backend.infraestructure.adapter.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);
}
