package com.EchoLearn_backend.infraestructure.rest.controler;

import com.EchoLearn_backend.application.usecases.User.UserUseCases;
import com.EchoLearn_backend.infraestructure.rest.dto.user.UserDto;
import com.EchoLearn_backend.infraestructure.rest.dto.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private final UserUseCases userService;

    public UserController(UserUseCases userService) {
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserResponse> create(@RequestBody UserDto userDto){
        try {
            return new ResponseEntity<>( this.userService.save(userDto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
