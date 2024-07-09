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

    private String name;

    private String url;

    private String dn;

    private String dnCompany;

    private String adLogin;

    private String adPassword;

    private String who_changed;

    private Date updateDate;

    @OneToOne
    private Company company;
//
//    @OneToOne
//    private Task task;

    private String prim;
}
