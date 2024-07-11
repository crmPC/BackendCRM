package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.Address;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByOrderByIdAddressAsc(Pageable pageable);
}
