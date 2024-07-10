package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
public class DomainAd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_domain_ad;

    @Column
    private String name;

    @Column
    private String url;

    @Column
    private String dn;

    @Column
    private String dnCompany;

    @Column
    private String adLogin;

    @Column
    private String adPassword;

    @Column
    private String who_changed;

    @Column
    private Date updateDate;

    @OneToOne
    private Company company;

    @OneToOne
    private Task task;

    @Column
    private String prim;
}
