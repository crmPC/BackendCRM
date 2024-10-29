package com.backend.crm.routes.controllers;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.DTOs.AuthUserDto;
import com.backend.crm.routes.DTOs.BanUserDto;
import com.backend.crm.routes.DTOs.SignupUserDto;
import com.backend.crm.routes.DTOs.SortDto;
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
    public Response signup(@RequestBody AuthUserDto userDto){
        return this.service.signup(userDto);
    }

    @PostMapping("/all")
    public Response findAll(@RequestHeader("Authorization") String authorization, @RequestBody SortDto sortDto){
        return this.service.findAllBySort(sortDto, authorization);
    }

    @GetMapping
    public Response getUser(@RequestHeader String Authorization, @RequestParam("id") Long id){
        return this.service.getUser(Authorization, id);
    }

    @PostMapping("/login")
    public Response authUser(@RequestBody AuthUserDto userDto){
        return this.service.loginUser(userDto);
    }

//    @PostMapping("/ban/{id}")
//    public Response banUser(@PathVariable Long id, @RequestHeader String Authorization, @RequestBody String BanReason){
//        return this.service.banUser(Authorization, id, BanReason);
//    }
}
