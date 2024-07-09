package com.backend.crm.routes.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GeneratedColumn;

@Data
@Entity
public class JobTittle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_job_title;

    private String name;

    private String description;
}
