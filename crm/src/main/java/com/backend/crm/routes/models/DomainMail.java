package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

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

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    @ManyToOne
    private Log log;
}
