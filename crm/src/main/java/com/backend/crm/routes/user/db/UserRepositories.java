package com.backend.crm.routes.user.db;

import com.backend.crm.routes.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositories extends JpaRepository<User, Long> {
}
