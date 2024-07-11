package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class WSGroupsUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idWsgroupsusers;

    @ManyToOne
    private WSUSer user;

    @ManyToOne
    private Company wsgroup;
}
