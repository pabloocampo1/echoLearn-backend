package com.EchoLearn_backend.infraestructure.rest.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestResetPassword {
    @NotNull
    private Integer token;
    @NotBlank
    private String password;
}
