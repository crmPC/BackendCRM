package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
public class UserEntity {
    @Id
    private Long id_user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column()
    private String patronymic;

    @Column()
    private Date dob;

    @Column()
    private String login;

    @Column()
    private String password;

    @Column()
    private boolean banned;

    @Column()
    private String banReason;

    @Column()
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.NOT_ACTIVATED;
}
