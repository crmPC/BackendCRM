package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class AccessUsersCompany{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_access_users_company;

    @ManyToOne
    private UserEntity userEntity;

    @ManyToOne
    private Company company;
}
