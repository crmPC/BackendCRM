package com.backend.crm.routes.models;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.jdbc.core.SqlReturnType;

import java.util.List;

@Data
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_company;

    private String name;

    private String contact;

    //private DomainAd domainAd;

    private Long fk_contact_wsuser;

    //private WSUSer contactuser;

    private Long fk_id_address;

    @OneToOne
    private Address address;

    //private FormatEnum format;

    private String INN;

    private String prefix;

    private String email_domain;

    private String description;

    //private List<DomainMail> domainMail;

    //private List<OfficeEquip> officeequip;
}
