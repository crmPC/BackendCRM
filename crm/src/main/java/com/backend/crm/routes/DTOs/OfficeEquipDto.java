package com.backend.crm.routes.DTOs;

import com.backend.crm.routes.models.Company;
import com.backend.crm.routes.models.OfficeEquipTypes;
import com.backend.crm.routes.models.Vendor;

import lombok.Data;

import java.sql.Date;

@Data
public class OfficeEquipDto {
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

    private OfficeEquipTypes officeEquipType;
}
