package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Entity
public class OfficeEquip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOfficeEquip;

    @Column
    private Long fk_id_office_equip_types;

    @ManyToOne
    @JoinColumn(name = "idVendor")
    private Vendor vendor;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String serial;

    @Column
    private String inventoryNumber;

    @Column
    private Date dateIn;

    @Column
    private Date dateOut;

    @Column
    private String prim;

    @Column
    private String model;

    @ManyToOne
    private OfficeEquipTypes officeequiptypes;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    @ManyToOne
    private Log log;
}
