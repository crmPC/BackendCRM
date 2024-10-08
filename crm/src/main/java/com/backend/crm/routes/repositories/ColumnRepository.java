package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.Columns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnRepository extends JpaRepository<Columns, Long> {
}
