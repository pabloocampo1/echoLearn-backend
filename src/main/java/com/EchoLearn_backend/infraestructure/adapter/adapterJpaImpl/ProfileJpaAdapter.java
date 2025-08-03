package com.EchoLearn_backend.infraestructure.adapter.adapterJpaImpl;

import com.EchoLearn_backend.Exception.ResourceNotFoundException;
import com.EchoLearn_backend.infraestructure.adapter.entity.ProfileEntity;
import com.EchoLearn_backend.infraestructure.adapter.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileJpaAdapter {
    @Autowired
    private final ProfileRepository profileRepository;


    public ProfileJpaAdapter(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ProfileEntity getById(Integer profileId){

        return this.profileRepository.getById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));
    };

    public ProfileEntity save(ProfileEntity profile) {
        return this.profileRepository.save(profile);
    }


}
