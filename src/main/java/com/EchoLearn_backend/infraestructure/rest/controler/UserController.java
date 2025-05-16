package com.EchoLearn_backend.infraestructure.rest.controler;

import com.EchoLearn_backend.application.mapper.UserMapper;
import com.EchoLearn_backend.application.usecases.User.UserUseCases;
import com.EchoLearn_backend.domain.model.User;
import com.EchoLearn_backend.infraestructure.rest.dto.user.UserDto;
import com.EchoLearn_backend.infraestructure.rest.dto.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private final UserUseCases userService;

    @Autowired
    private final UserMapper userMapper;

    public UserController(UserUseCases userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponse>> getAll(){
        List<User> userResponseList = this.userService.getAll();
        return new ResponseEntity<>(userResponseList.stream().map(this.userMapper::userToResponse).toList(), HttpStatus.OK);
    }


}
