package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class WSGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEsGroup;

    @ManyToOne
    private DomainAd domainAd;

    @Column
    private String objectGUID;

    @Column
    private String dn;

    @Column
    private String cn;

    @ManyToMany
    private List<WSUSer> wsuser;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;
}
