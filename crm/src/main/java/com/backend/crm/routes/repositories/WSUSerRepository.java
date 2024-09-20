package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.WSUSer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WSUSerRepository extends JpaRepository<WSUSer, Long>, JpaSpecificationExecutor<WSUSer> {
}
