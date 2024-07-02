package com.backend.crm.routes.services;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.repositories.UserRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepositories repositories;

    public Response signup(){
        try {
            return null;
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(),err.getMessage());
        }
    }
}
