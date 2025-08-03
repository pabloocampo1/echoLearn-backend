package com.EchoLearn_backend.infraestructure.adapter.repository;

import com.EchoLearn_backend.infraestructure.adapter.entity.ProfileEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {

    @Query(value = "SELECT p FROM ProfileEntity p WHERE p.id_profile = :profileId")
    Optional<ProfileEntity> getById(@Param("profileId") Integer profileId);
}
