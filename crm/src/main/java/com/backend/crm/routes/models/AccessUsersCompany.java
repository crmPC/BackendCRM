package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class AccessUsersCompany{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAccessUsersCompany;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private Company company;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;
}
