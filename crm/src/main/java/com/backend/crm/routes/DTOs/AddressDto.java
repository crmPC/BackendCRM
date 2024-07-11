package com.backend.crm.routes.DTOs;

import lombok.Data;

@Data
public class AddressDto {
    private String zipCode;

    private String country;

    private String region;

    private String city;

    private String street;

    private String house;

    private String apartment;
}
