package com.backend.crm.routes.DTOs;

import com.backend.crm.routes.models.*;

import lombok.Data;

import java.util.List;

@Data
public class CompanyDto {
    private String name;

    private String contact;

    private DomainAd domainAd;

    private WSUSer contactuser;

    private Address address;

    private FormatEnum format;

    private String inn;

    private String prefix;

    private String email_domain;

    private String description;

    private DomainMail domainMail;

    private OfficeEquip officeequip;
}
