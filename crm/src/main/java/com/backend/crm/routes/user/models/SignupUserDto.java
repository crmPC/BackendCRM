package com.backend.crm.routes.user.models;

import lombok.Data;

import java.sql.Date;

@Data
public class SignupUserDto {
    private String name;

    private String surname;

    private String patronymic;

    private String login;

    private String password;

    private Date dob;
}
