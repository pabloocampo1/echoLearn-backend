package com.EchoLearn_backend.infraestructure.adapter.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.EchoLearn_backend.infraestructure.adapter.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "role")
@Data
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Integer id_role;

    @Column(name = "name_role")
    private String name_role;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<UserEntity> users;

}
