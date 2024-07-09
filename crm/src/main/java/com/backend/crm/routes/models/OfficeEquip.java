package com.backend.crm.routes.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
public class OfficeEquip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_office_equip;

    private Long fk_id_company;

    private Long fk_id_office_equip_types;

    private Long fk_id_vendor;

    private Vendor vendor;

    private String name;

    private String description;

    private String serial;

    private String inventoryNumber;

    private Date dateIn;

    private Date dateOut;

    private String prim;

    private String model;

    private Company company;

    private OfficeEquipTypes officeEquipTypes;


}
