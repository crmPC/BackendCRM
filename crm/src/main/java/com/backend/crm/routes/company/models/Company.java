package com.backend.crm.routes.company.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    private Long id_company;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contac", nullable = false)
    private String contact;


}
