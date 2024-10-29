package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.UserEntity;
import com.backend.crm.routes.models.Vendor;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositories extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    UserEntity findByLogin(String login);
}
