package com.backend.crm.routes.DTOs;

import com.backend.crm.routes.models.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
public class UserDto {

    private String name;

    private String surname;

    private String patronymic;

    private Date dob;

    private String login;

    private String password;

    private boolean banned = false;

    private String banReason;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    private UserRole userRole;
}
