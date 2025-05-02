package com.EchoLearn_backend.infraestructure.rest.dto.auth;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AuthResponse {
    private final String username;
    private final String message;
    private final String jwt;
    private final Boolean isAuthenticate;
    private final String roles;
    private final LocalDateTime timestamp;

    @Builder
    public AuthResponse(String username, String message, String jwt, Boolean isAuthenticate, String roles, LocalDateTime timestamp) {
        this.username = username;
        this.message = message;
        this.jwt = jwt;
        this.isAuthenticate = isAuthenticate;
        this.roles = roles;
        this.timestamp = timestamp;
    }
}
