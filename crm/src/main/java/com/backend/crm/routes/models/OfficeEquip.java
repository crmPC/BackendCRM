package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
public class OfficeEquip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_office_equip;

    @Column
    private Long fk_id_company;

    @Column
    private Long fk_id_office_equip_types;

    @ManyToOne
    @JoinColumn(name = "id_vendor")
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

    @OneToOne
    private Company company;

    @ManyToOne
    @JoinColumn(name = "id_office_equip_types")
    private OfficeEquipTypes officeEquipType;

}
