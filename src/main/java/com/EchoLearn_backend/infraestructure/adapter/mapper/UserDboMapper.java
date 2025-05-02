package com.EchoLearn_backend.infraestructure.adapter.mapper;


import com.EchoLearn_backend.domain.model.User;
import com.EchoLearn_backend.infraestructure.adapter.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDboMapper {

    public User toDomain(UserEntity userEntity){
        return User
                .builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail())
                .photo(userEntity.getPhoto())
                .build();
    }

    public UserEntity toDbo(User user){
        return UserEntity
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .photo(user.getPhoto())
                .build();
    }


}
