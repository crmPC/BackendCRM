package com.backend.crm.routes.DTOs;

import com.backend.crm.routes.models.CommunicationTypeEnum;
import lombok.Data;

@Data
public class CommunicationDto {
    private CommunicationTypeEnum type;

    private String value;
}
