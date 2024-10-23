package com.backend.crm.routes.DTOs;

import com.backend.crm.routes.models.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
public class WSUSerDto {
    private String fullname;

    private LocalDate dob;

    private String login;

    private String prim;

    private JobTittle jobtitle;

    private Company company;

    private OfficeEquip officeequip;

    private Email email;

    private Communication communication;

    private Pass pass;

    private String password;
}
