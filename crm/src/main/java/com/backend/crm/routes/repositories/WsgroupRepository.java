package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.WSGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WsgroupRepository extends JpaRepository<WSGroup, Long> {
}
