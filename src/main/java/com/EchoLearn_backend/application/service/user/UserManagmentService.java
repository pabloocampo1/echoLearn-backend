package com.EchoLearn_backend.application.service.user;

import com.EchoLearn_backend.application.mapper.UserMapper;
import com.EchoLearn_backend.application.usecases.User.UserUseCases;
// import com.EchoLearn_backend.domain.model.User;
import com.EchoLearn_backend.domain.model.User;
import com.EchoLearn_backend.domain.port.UserPersistencePort;
import com.EchoLearn_backend.infraestructure.adapter.mapper.UserDboMapper;
import com.EchoLearn_backend.infraestructure.rest.dto.user.UserDto;
import com.EchoLearn_backend.infraestructure.rest.dto.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserManagmentService implements UserUseCases {
    @Autowired
    private final UserPersistencePort userPersistencePort;

    @Autowired
    private final UserDboMapper userDboMapper;
    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private  PasswordEncoder passwordEncoder;
    public UserManagmentService(UserPersistencePort userPersistencePort, UserDboMapper userDboMapper,  UserMapper userMapper) {
        this.userPersistencePort = userPersistencePort;
        this.userDboMapper = userDboMapper;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserResponse save(UserDto user) {
       try{
           User userDomain = this.userPersistencePort.save(this.userMapper.toDomain(user));
           return this.userMapper.userToResponse(userDomain);
       } catch (Exception e) {
           e.printStackTrace();
           throw new RuntimeException(e);
       }
    }
}
