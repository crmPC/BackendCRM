package com.backend.crm.routes.DTOs;

import com.backend.crm.routes.models.DomainMail;

import lombok.Data;

@Data
public class EmailDto {
    private String name;

    private String name_with_domain;

    private String password;

    private DomainMail domainmail;
}
