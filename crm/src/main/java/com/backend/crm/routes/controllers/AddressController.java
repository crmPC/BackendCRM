package com.backend.crm.routes.controllers;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.DTOs.AddressDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Сервис адресов")
@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService service;

    @Operation(summary = "Создать новый адресс")
    @PostMapping
    public Response saveAddress(@RequestHeader("Authorization") String authorization, @RequestBody AddressDto addressDto){
        return this.service.save(addressDto, authorization);
    }

    @Operation(summary = "Получить все адреса с сортровкой (передается параметром)")
    @PostMapping("/all")
    public Response findAllAddressBySort(@RequestHeader("Authorization") String authorization, @RequestBody SortDto sortDto){
        return this.service.findAllBySort(sortDto);
    }

    @Operation(summary = "Изменить существующий адрес")
    @PutMapping
    public Response saveEditAddress(@RequestHeader("Authorization") String authorization, @RequestBody AddressDto addressDto, @RequestParam("id") Long id){
        return this.service.saveEdit(addressDto, id);
    }

    @Operation(summary = "Найти адрес")
    @GetMapping
    public Response findAddressById(@RequestHeader("Authorization") String authorization, @RequestParam("id") Long id){
        return this.service.findById(id);
    }

    @Operation(summary = "Удалить адрес")
    @DeleteMapping
    public Response deleteAddressById(@RequestHeader("Authorization") String authorization, @RequestParam("id") Long id){
        return this.service.deleteById(id);
    }
}
