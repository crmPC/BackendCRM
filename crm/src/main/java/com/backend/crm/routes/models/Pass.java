package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Pass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPass;

    @Column
    private String name;

    @OneToOne
    @JoinColumn(name = "fk_pass")
    private WSUSer wsuser;
}
