package com.EchoLearn_backend.domain.port;


import com.EchoLearn_backend.domain.model.User;

import java.util.List;

public interface UserPersistencePort {
    User save(User user);
    Boolean existByEmail(String email);
    User findByEmail(String email);
    User getById(Integer user_id);
    List<User> getAll();

}
