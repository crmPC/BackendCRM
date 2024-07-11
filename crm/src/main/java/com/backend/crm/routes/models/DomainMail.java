package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class DomainMail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDomainMail;

    @ManyToOne
    private Company company;

    @Column
    private String name;

    @Column
    private String who_changed;

    @ManyToMany
    private List<Email> email;
}
