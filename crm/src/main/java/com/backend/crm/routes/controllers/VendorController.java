package com.backend.crm.routes.controllers;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.DTOs.OfficeEquipTypesDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.DTOs.VendorDto;
import com.backend.crm.routes.services.VendorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Сервис производителей техники")
@RestController
@RequestMapping("/vendor")
@RequiredArgsConstructor
public class VendorController {
    private final VendorService service;

    @PostMapping("/all")
    @Operation(summary = "Получить производителей")
    public Response findAllVendorBySort(@RequestBody SortDto sortDto){
        return this.service.findAllBySort(sortDto);
    }

    @PostMapping
    @Operation(summary = "Добавить производителя")
    public Response saveVendor(@RequestBody VendorDto dto){
        return this.service.save(dto);
    }

    @PutMapping
    @Operation(summary = "Изменить производителя")
    public Response editVendor(@RequestParam("id") Long id, @RequestBody VendorDto dto){
        return this.service.saveEdit(id, dto);
    }

    @DeleteMapping
    @Operation(summary = "Удалить производителя")
    public Response deleteVendorById(@RequestParam Long id){
        return this.service.deleteById(id);
    }

    @GetMapping
    @Operation(summary = "Получить производителя")
    public Response findVendorById(@RequestParam Long id){
        return this.service.findById(id);
    }
}
