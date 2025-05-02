package com.EchoLearn_backend.infraestructure.rest.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank
    String username;
    @NotBlank
    String password;
}
