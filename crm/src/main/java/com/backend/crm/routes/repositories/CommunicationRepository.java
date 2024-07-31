package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.Communication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunicationRepository extends JpaRepository<Communication, Long> {
}
