package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.AllowedIp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AllowedIpRepository extends JpaRepository<AllowedIp, Long>, JpaSpecificationExecutor<AllowedIp> {
}
