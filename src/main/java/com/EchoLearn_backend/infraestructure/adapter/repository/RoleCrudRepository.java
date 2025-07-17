package com.EchoLearn_backend.infraestructure.adapter.repository;

import com.EchoLearn_backend.infraestructure.adapter.entity.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleCrudRepository extends CrudRepository<RoleEntity, Integer> {
    @Query("SELECT r FROM RoleEntity r WHERE r.name_role = :name")
    Optional<RoleEntity> findByName(@Param("name") String name);
}
