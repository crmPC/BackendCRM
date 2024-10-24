package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    private DomainAd domainAd;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

//    @ManyToMany
//    private List<Log> log;
}
