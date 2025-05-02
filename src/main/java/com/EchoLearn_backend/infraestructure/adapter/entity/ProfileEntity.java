package com.EchoLearn_backend.infraestructure.adapter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profile")
    private Integer id_profile;

    @OneToOne(mappedBy = "profile")
    private UserEntity user;

    private Integer points;

    private Integer num_exams_takes;

    private Integer wins;

    private Integer  failed;

    @Column(nullable = false)
    private Boolean available;
}
