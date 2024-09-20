package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.JobTittle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JobTittleRepository extends JpaRepository<JobTittle, Long>, JpaSpecificationExecutor<JobTittle> {
}
