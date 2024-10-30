package com.backend.crm.routes.DTOs;

import com.backend.crm.routes.models.Company;
import com.backend.crm.routes.models.UserEntity;
import lombok.Data;

@Data
public class AccessUsersCompanyDto {

    private UserEntity user;

    private Company company;
}
