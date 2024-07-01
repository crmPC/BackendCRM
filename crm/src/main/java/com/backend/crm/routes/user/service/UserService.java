package com.backend.crm.routes.user.service;

import com.backend.crm.routes.user.db.UserRepositories;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepositories repositories;

    public UserService(final UserRepositories repositories) {
        this.repositories = repositories;
    }
}
