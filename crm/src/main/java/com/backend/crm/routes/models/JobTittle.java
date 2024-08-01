package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class JobTittle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJobTitle;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    public JobTittle(String description, String name, LocalDateTime createdAt) {
        this.description = description;
        this.name = name;
        this.createdAt = createdAt;
    }
}