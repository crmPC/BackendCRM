package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.OfficeEquip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeEquipRepository extends JpaRepository<OfficeEquip, Long>, JpaSpecificationExecutor<OfficeEquip> {
}
