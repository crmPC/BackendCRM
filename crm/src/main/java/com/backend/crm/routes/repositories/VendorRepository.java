package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
