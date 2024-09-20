package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.OfficeEquipTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OfficeEquipTypesRepository extends JpaRepository<OfficeEquipTypes, Long>, JpaSpecificationExecutor<OfficeEquipTypes> {
}
