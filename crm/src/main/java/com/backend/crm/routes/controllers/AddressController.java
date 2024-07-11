package com.backend.crm.routes.controllers;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.DTOs.AddressDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.services.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Payload;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.Sort;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Сервис адресов")
@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService service;

    @PostMapping
    public Response saveAddress(@RequestBody AddressDto addressDto){
        return this.service.saveAddress(addressDto);
    }

    @PostMapping("/all")
    public Response findAllWithSort(@RequestBody SortDto sortDto){
        return this.service.findAllWithSort(sortDto);
    }
}
