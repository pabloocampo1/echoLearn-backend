package com.EchoLearn_backend.infraestructure.adapter.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "token_reset_password")
public class TokenResetPassword {
    @Id
    @Column(nullable = false, unique = true)
    private Integer token;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDateTime expiredTime;
}
