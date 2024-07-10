package com.backend.crm.routes.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "CompanyEntity")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_company;

    private String name;

    private String contact;

    @ManyToOne
    private DomainAd domainAd;

    @ManyToOne
    private WSUSer contactuser;

    @OneToOne
    private Address address;

    @Column
    private FormatEnum format;

    @Column
    private String INN;

    @Column
    private String prefix;

    @Column
    private String email_domain;

    @Column
    private String description;

    @ManyToMany
    private List<DomainMail> domainMail;

    @ManyToMany
    private List<OfficeEquip> officeequip;
}
