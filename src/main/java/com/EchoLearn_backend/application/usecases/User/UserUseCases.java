package com.EchoLearn_backend.application.usecases.User;


import com.EchoLearn_backend.domain.model.User;
import com.EchoLearn_backend.infraestructure.rest.dto.user.UserDto;
import com.EchoLearn_backend.infraestructure.rest.dto.user.UserResponse;

import java.util.List;

public interface UserUseCases {
    UserResponse save(UserDto user);
    List<User> getAll();
}
