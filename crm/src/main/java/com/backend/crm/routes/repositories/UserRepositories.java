package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositories extends JpaRepository<UserEntity, Long> {
}
