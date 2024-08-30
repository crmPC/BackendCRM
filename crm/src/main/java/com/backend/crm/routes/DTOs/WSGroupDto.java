package com.backend.crm.routes.DTOs;

import com.backend.crm.routes.models.DomainAd;
import com.backend.crm.routes.models.WSUSer;
import lombok.Data;

import java.util.List;

@Data
public class WSGroupDto {
    private DomainAd domainAd;

    private String objectGUID;

    private String dn;

    private String cn;

    private List<WSUSer> wsuser;
}
