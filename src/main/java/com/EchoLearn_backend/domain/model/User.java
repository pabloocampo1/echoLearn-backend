package com.EchoLearn_backend.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private Integer user_id;

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

    private String photo;

    private Integer profile_id;
}
