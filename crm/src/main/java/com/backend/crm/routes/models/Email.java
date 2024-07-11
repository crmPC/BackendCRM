package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmail;

    @Column
    private String name;

    @Column
    private String name_with_domain;

    @Column
    private String password;

    @ManyToOne
    private DomainMail domainMail;
}
