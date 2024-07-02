package com.backend.crm.routes.DTOs;

import lombok.Data;

@Data
public class AuthUserDto {
    private String login;

    private String password;
}
