package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class AllowedIp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAllowedIp;

    @Column(columnDefinition = "INET")
    private String address;

    @Column
    private String prim;
}
