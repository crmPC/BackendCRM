package com.backend.crm.routes.controllers;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.DTOs.AuthUserDto;
import com.backend.crm.routes.DTOs.BanUserDto;
import com.backend.crm.routes.DTOs.SignupUserDto;
import com.backend.crm.routes.services.UserService;


import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;


@Tag(name = "userService")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("/signup")
    public Response signup(@RequestBody SignupUserDto userDto){
        return null;
    }

    @GetMapping()
    public Response getUser(@RequestBody String Authorization){
        return null;
    }

    @PostMapping("/auth")
    public Response authUser(@RequestBody AuthUserDto userDto){
        return null;
    }

    @PostMapping("/ban")
    public Response banUser(@RequestBody BanUserDto banUserDto){
        return null;
    }
}
