package com.backend.crm.app.domain;

import com.backend.crm.routes.models.UserEntity;
import com.backend.crm.routes.models.UserRole;
import com.backend.crm.routes.services.CompanyService;
import com.backend.crm.routes.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ValidateService {
    private final TokenService tokenService;

    private final UserService userService;

    private final CompanyService companyService;

    /**
     * Проверка доступа к запросу
     */


    public boolean checkAccess(List<UserRole> roles, UserEntity user) {
        return roles.stream().anyMatch(role -> role.equals(user.getUserRole()));
    }

    public UserEntity getUserFromJWT(String token){
        try {
            return userService.getUserForAuth(tokenService.getUserIdFromJWT(token));
        }catch (Exception err){
            System.out.println(err.getMessage());
            return null;
        }
    }

    public UserEntity validateTokenByToken(String token) {
        if (tokenService.validateToken(token)) {
            return getUserFromJWT(token);
        }

        return null;
    }
}
