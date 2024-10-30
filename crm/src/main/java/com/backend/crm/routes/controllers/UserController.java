package com.backend.crm.routes.controllers;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.DTOs.*;
import com.backend.crm.routes.services.UserService;


import io.swagger.v3.oas.annotations.Operation;
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

    @PutMapping("/ban")
    public Response banUser(@RequestHeader String Authorization, @RequestBody BanUserDto BanReason){
        return this.service.banUser(Authorization, BanReason);
    }

    @PutMapping
    @Operation(summary = "Изменить пользователя")
    public Response editWSUSer(@RequestParam("id") Long id, @RequestBody UserDto dto){
        return this.service.saveEdit(id, dto);
    }

    @DeleteMapping
    @Operation(summary = "Удалить пользователя")
    public Response deleteWSUSerById(@RequestParam Long id){
        return this.service.deleteById(id);
    }
}
