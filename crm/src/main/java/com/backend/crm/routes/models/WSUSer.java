package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class WSUSer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idWsUser;

    @Column
    private String fullname;

    @Column
    private LocalDate dob;

    @Column
    private String login;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Email email;

    @ManyToOne
    private OfficeEquip officeequip;

    @ManyToOne
    private JobTittle jobtitle;

    @ManyToOne
    private Pass pass;

    @ManyToOne
    private Communication communication;

    @Column
    private String prim;

    @Column
    private String password;

    @Column
    private String name;

    @ManyToOne
    private DomainAd domainAd;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    @ManyToOne
    private Log log;
}
