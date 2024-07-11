package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class AccessUsersCompany{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAccessUsersCompany;

    @ManyToOne
    private UserEntity userEntity;

    @ManyToOne
    private Company company;
}
