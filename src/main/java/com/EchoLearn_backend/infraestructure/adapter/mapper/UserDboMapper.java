package com.EchoLearn_backend.infraestructure.adapter.mapper;


import com.EchoLearn_backend.domain.model.User;
import com.EchoLearn_backend.infraestructure.adapter.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDboMapper {

    public User toDomain(UserEntity userEntity){
        return User
                .builder()
                .user_id(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail())
                .profile_id(userEntity.getProfile().getId_profile())
                .photo(userEntity.getPhoto())
                .build();
    }

    public UserEntity toDbo(User user){
        return UserEntity
                .builder()
                .id(user.getUser_id())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .photo(user.getPhoto())
                .build();
    }


}
