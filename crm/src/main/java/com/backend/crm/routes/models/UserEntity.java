package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String patronymic;

    @Column
    private Date dob;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private boolean banned = false;

    @Column
    private String banReason;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.NOT_ACTIVATED;
}
