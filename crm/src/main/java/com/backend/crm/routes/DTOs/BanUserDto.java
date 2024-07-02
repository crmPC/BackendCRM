package com.backend.crm.routes.DTOs;

import lombok.Data;

@Data
public class BanUserDto {
    private Long idUser;

    private String banReason;
}
