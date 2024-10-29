package com.backend.crm.routes.controllers;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.DTOs.AccessUsersCompanyDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.services.AccessUsersCompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Сервис админов компании")
@RestController
@RequestMapping("/accessuserscompany")
@RequiredArgsConstructor
public class AccessUsersCompanyController {
    private final AccessUsersCompanyService service;

    @Operation(summary = "Получить всех пользователи привязыннх к компнии с сортровкой (передается параметром)")
    @PostMapping("/all")
    public Response findAllAddressBySort(@RequestHeader("Authorization") String authorization, @RequestBody SortDto sortDto){
        return this.service.findAllBySort(sortDto, authorization);
    }

    @Operation(summary = "Создать пользователя и привязать его к компании")
    @PostMapping
    public Response saveAddress(@RequestHeader("Authorization") String authorization, @RequestBody AccessUsersCompanyDto accessUsersCompanyDto){
        return this.service.save(accessUsersCompanyDto, authorization);
    }

    @Operation(summary = "Изменить существующего привязанного пользователя")
    @PutMapping
    public Response saveEditAddress(@RequestHeader("Authorization") String authorization, @RequestBody AccessUsersCompanyDto accessUsersCompanyDto, @RequestParam("id") Long id){
        return this.service.saveEdit(id, accessUsersCompanyDto, authorization);
    }

    @Operation(summary = "Получить привязанного пользоваетеля")
    @GetMapping
    public Response findAddressById(@RequestHeader("Authorization") String authorization, @RequestParam("id") Long id){
        return this.service.findById(id, authorization);
    }

    @Operation(summary = "Удалить привязанного пользователя")
    @DeleteMapping
    public Response deleteAddressById(@RequestHeader("Authorization") String authorization, @RequestParam("id") Long id){
        return this.service.deleteById(id, authorization);
    }
}
