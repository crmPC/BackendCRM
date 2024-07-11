package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OfficeEquipTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOfficeEquipTypes;

    @Column
    private String name;

    @Column
    private String description;
}
