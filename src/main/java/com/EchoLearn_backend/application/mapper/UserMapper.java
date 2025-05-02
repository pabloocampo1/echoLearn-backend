package com.EchoLearn_backend.application.mapper;

import com.EchoLearn_backend.domain.model.User;
import com.EchoLearn_backend.infraestructure.rest.dto.user.UserDto;
import com.EchoLearn_backend.infraestructure.rest.dto.user.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toDomain(UserDto userDto){
        return User
                .builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .build();
    }

    public UserResponse userToResponse(User user){
        return UserResponse
                .builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .photo(user.getPhoto())
                .build();
    }
}
