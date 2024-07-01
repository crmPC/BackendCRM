package com.backend.crm.routes.user.controller;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.user.models.SignupUserDto;
import com.backend.crm.routes.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Юзер сервис")
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    public UserController(final UserService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    public Response signup(@RequestBody SignupUserDto userDto){
        return null;
    }
}
