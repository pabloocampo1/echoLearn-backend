package com.EchoLearn_backend.domain.port;


import com.EchoLearn_backend.domain.model.User;

public interface UserPersistencePort {
    User save(User user);

}
