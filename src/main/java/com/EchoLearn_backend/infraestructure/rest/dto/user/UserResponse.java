package com.EchoLearn_backend.infraestructure.rest.dto.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponse {
    private String username;
    private String email;
    private String photo;
}
