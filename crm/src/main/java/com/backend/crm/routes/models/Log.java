package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLog;

    @ManyToOne
    private UserEntity fk_id_user;

    @Column
    private String action_type;

    @Column
    private Date time;

    @Column
    private String details;
}
