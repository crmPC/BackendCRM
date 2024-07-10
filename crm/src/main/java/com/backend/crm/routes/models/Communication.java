package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Communication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_communication;

    @Column
    private CommunicationTypeEnum type;

    @Column
    private String value;

    @ManyToOne
    private WSUSer wsuser;
}
