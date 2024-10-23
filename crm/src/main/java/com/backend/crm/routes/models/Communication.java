package com.backend.crm.routes.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Communication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommunication;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private CommunicationTypeEnum type;

    @Column
    private String value;

//    @ManyToOne
//    private WSUSer wsuser;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    @ManyToOne
    private Log log;

    public Communication(CommunicationTypeEnum type, String value) {
        this.type = type;
        this.value = value;
    }
}
