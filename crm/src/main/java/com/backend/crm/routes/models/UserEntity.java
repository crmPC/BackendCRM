package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

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
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public boolean getBanned() {
        return banned;
    }
}
