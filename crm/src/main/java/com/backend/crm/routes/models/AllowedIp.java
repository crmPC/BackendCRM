package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class AllowedIp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAllowedIp;

    @Column
    private String address;

    @Column
    private String prim;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

}
