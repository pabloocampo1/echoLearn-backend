package com.EchoLearn_backend.infraestructure.rest.dto.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponse {
    private Integer user_id;
    private String username;
    private String email;
    private String photo;
    private Integer profile_id;
}
