package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.AccessUsersCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessUsersCompanyRepository extends JpaRepository<AccessUsersCompany, Long> {
}
