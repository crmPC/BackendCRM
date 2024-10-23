package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "logInfo")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLog;

    @ManyToOne
    private WSUSer wsuSer;

    @Column
    private String action_type;

    @Column
    private LocalDateTime time;

    @Column
    private String details;
}
