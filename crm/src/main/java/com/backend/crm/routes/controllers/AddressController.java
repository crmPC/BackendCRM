package com.backend.crm.routes.controllers;

import com.backend.crm.routes.DTOs.AddressDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Сервис адресов")
@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService service;

    @Operation(summary = "Создать новый адресс")
    @PostMapping
    public ResponseEntity saveAddress(@RequestHeader("Authorization") String authorization, @RequestBody AddressDto addressDto){
        return this.service.save(addressDto, authorization);
    }

    @Operation(summary = "Получить все адреса с сортровкой (передается параметром)")
    @PostMapping("/all")
    public ResponseEntity findAllAddressBySort(@RequestHeader("Authorization") String authorization, @RequestBody SortDto sortDto){
        return this.service.findAllBySort(sortDto, authorization);
    }

    @Operation(summary = "Изменить существующий адрес")
    @PutMapping
    public ResponseEntity saveEditAddress(@RequestHeader("Authorization") String authorization, @RequestBody AddressDto addressDto, @RequestParam("id") Long id){
        return this.service.saveEdit(addressDto, id, authorization);
    }

    @Operation(summary = "Найти адрес")
    @GetMapping
    public ResponseEntity findAddressById(@RequestHeader("Authorization") String authorization, @RequestParam("id") Long id){
        return this.service.findById(id, authorization);
    }

    @Operation(summary = "Удалить адрес")
    @DeleteMapping
    public ResponseEntity deleteAddressById(@RequestHeader("Authorization") String authorization, @RequestParam("id") Long id){
        return this.service.deleteById(id, authorization);
    }
}
