package com.backend.crm.routes.controllers;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.DTOs.OfficeEquipDto;
import com.backend.crm.routes.DTOs.OfficeEquipTypesDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.services.OfficeEquipTypesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Сервис типов оргТехники")
@RestController
@RequestMapping("/officeequiptypes")
@RequiredArgsConstructor
public class OfficeEquipTypesController {
    private final OfficeEquipTypesService service;

    @PostMapping("/all")
    @Operation(summary = "Получить все типы оргТехники")
    public Response findAllOfficeEquipTypesBySort(@RequestBody SortDto sortDto){
        return this.service.findAllBySort(sortDto);
    }

    @PostMapping
    @Operation(summary = "Добавить тип оргТехники")
    public Response saveOfficeEquipTypes(@RequestBody OfficeEquipTypesDto dto){
        return this.service.save(dto);
    }

    @PutMapping
    @Operation(summary = "Изменить тип оргТехники")
    public Response editOfficeEquipTypes(@RequestParam("id") Long id, @RequestBody OfficeEquipTypesDto dto){
        return this.service.saveEdit(id, dto);
    }

    @DeleteMapping
    @Operation(summary = "Удалить тип оргТехники")
    public Response deleteOfficeEquipTypesById(@RequestParam Long id){
        return this.service.deleteById(id);
    }

    @GetMapping
    @Operation(summary = "Получить тип оргТехники")
    public Response findOfficeEquipTypesById(@RequestParam Long id){
        return this.service.findById(id);
    }
}
