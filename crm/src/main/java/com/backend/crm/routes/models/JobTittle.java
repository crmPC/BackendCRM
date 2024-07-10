package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class JobTittle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_job_title;

    @Column
    private String name;

    @Column
    private String description;
}
