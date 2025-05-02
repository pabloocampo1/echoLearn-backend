package com.EchoLearn_backend.infraestructure.adapter.repository;

import com.EchoLearn_backend.infraestructure.adapter.entity.ProfileEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {
}
