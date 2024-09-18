package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

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

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;
}
