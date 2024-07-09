package com.backend.crm.routes.models;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_address;

    private String zipcode;

    private String country;

    private String region;

    private String city;

    private String street;

    private String house;

    private String apartment;

    @OneToOne
    private Company company;
}
