package com.backend.crm.routes.DTOs;

import com.backend.crm.routes.models.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
public class WSUSerDto {
    private String fullname;

    private LocalDate dob;

    private String prim;

    private JobTittle jobTittle;

    private Company company;

    private OfficeEquip officeEquip;

    private Email email;

    private Communication communication;

    private Pass pass;

    private String password;
}
