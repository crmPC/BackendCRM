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
    public Response saveAddress(@RequestBody AddressDto addressDto){
        return this.service.saveAddress(addressDto);
    }

    @Operation(summary = "Получить все адреса с сортровкой (передается параметром)")
    @PostMapping("/all")
    public Response findAllWithSort(@RequestBody SortDto sortDto){
        return this.service.findAllWithSort(sortDto);
    }

    @Operation(summary = "Изменить существующий адрес")
    @PutMapping
    public Response saveEditAddress(@RequestBody AddressDto addressDto, @RequestParam("id") Long id){
        return this.service.saveEditAddress(addressDto, id);
    }

    @Operation(summary = "Найти адрес")
    @GetMapping
    public Response findAddressById(@RequestParam("id") Long id){
        return this.service.findAddressById(id);
    }

    @Operation(summary = "Удалить адрес")
    @DeleteMapping
    public Response deleteAddressById(@RequestParam("id") Long id){
        return this.service.deleteAddressById(id);
    }
}
