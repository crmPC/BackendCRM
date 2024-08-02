package com.backend.crm.routes.DTOs;

import com.backend.crm.routes.models.Company;
import lombok.Data;

@Data
public class DomainMailDto {
    private Company company;

    private Long fk_id_company;

    private String name;
}
