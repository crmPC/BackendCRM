package com.backend.crm.routes.user.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
public class User {
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

    @Column(nullable = false, columnDefinition = "false")
    private boolean banned;

    @Column()
    private String banReason;

    @Column()
    private UserRole userRole = UserRole.NOT_ACTIVATED;
}
