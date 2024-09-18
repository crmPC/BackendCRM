package com.backend.crm.routes.controllers;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.DTOs.OfficeEquipDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.DTOs.WSGroupDto;
import com.backend.crm.routes.DTOs.WSUSerDto;
import com.backend.crm.routes.services.OfficeEquipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Сервис оргТехники")
@RestController
@RequestMapping("/officeequip")
@RequiredArgsConstructor
public class OfficeEquipController {
    private final OfficeEquipService service;

    @PostMapping("/all")
    @Operation(summary = "Получить всю оргТехнику")
    public Response findAllOfficeEquipBySort(@RequestBody SortDto sortDto){
        return this.service.findAllBySort(sortDto);
    }

    @PostMapping
    @Operation(summary = "Добавить оргТехнику")
    public Response saveOfficeEquip(@RequestBody OfficeEquipDto dto){
        return this.service.save(dto);
    }

    @PutMapping
    @Operation(summary = "Изменить оргТехнику")
    public Response editOfficeEquip(@RequestParam("id") Long id, @RequestBody OfficeEquipDto dto){
        return this.service.saveEdit(id, dto);
    }

    @DeleteMapping
    @Operation(summary = "Удалить оргТехнику")
    public Response deleteOfficeEquipById(@RequestParam Long id){
        return this.service.deleteById(id);
    }

    @GetMapping
    @Operation(summary = "Получить оргТехнику")
    public Response findOfficeEquipById(@RequestParam Long id){
        return this.service.findById(id);
    }
}
