package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.DomainMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainMailRepository extends JpaRepository<DomainMail, Long> {
}
