package com.backend.crm.routes.controllers;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.DTOs.WSGroupDto;
import com.backend.crm.routes.DTOs.WSUSerDto;
import com.backend.crm.routes.services.WsgroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Группы пользователей")
@RestController
@RequestMapping("/wsgroup")
@RequiredArgsConstructor
public class WsgroupController {

    private final WsgroupService service;

    @PostMapping("/all")
    @Operation(summary = "Получить все группы пользователей")
    public Response findAllWsgroupBySort(@RequestBody SortDto sortDto){
        return this.service.findAllBySort(sortDto);
    }

    @PostMapping
    @Operation(summary = "Добавить группу пользователей")
    public Response saveWsgroup(@RequestBody WSUSerDto dto){
        return this.service.save(dto);
    }

    @PutMapping
    @Operation(summary = "Изменить группу пользователей")
    public Response editWsgroup(@RequestParam("id") Long id, @RequestBody WSGroupDto dto){
        return this.service.saveEdit(id, dto);
    }

    @DeleteMapping
    @Operation(summary = "Удалить группу пользователей")
    public Response deleteWsgroupById(@RequestParam Long id){
        return this.service.deleteById(id);
    }

    @GetMapping
    @Operation(summary = "Получить группу пользователей")
    public Response findWsgroupById(@RequestParam Long id){
        return this.service.findById(id);
    }
}
