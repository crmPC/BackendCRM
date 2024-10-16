package com.backend.crm.routes.models;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "CompanyEntity")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompany;

    @Column
    private String name;

    @Column
    private String contact;

    @ManyToOne
    private DomainAd domainAd;

    @ManyToOne
    private WSUSer contactuser;

    @ManyToOne
    private Address address;

    @Column
    private FormatEnum format;

    @Column(name = "inn")
    private String inn;

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

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;
}
