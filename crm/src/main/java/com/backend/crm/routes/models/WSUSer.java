package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
public class WSUSer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idWsUser;

    @Column
    private String fullname;

    @Column
    private Date dob;

    @OneToOne
    private Company company;

    @ManyToOne
    private Email email;

    @OneToOne
    private OfficeEquip officeEquip;

    @ManyToOne
    private JobTittle jobTittle;

    @ManyToOne
    private Communication communication;

    @OneToOne
    private Pass pass;

    @Column
    private String prim;

    @Column
    private String password;

    @ElementCollection
    private String[] objectClass;

    @Column
    private String member;

    @Column
    private String cn;

    @Column
    private String sn;

    @Column
    private String displayName;

    @ElementCollection
    private String[] memberOf;

    @Column
    private String name;

    @Column
    private String objectGUID;

    @Column
    private String userPrincipalName;

    @ManyToOne
    private DomainAd domainAd;
}
