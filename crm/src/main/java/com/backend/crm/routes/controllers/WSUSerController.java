package com.backend.crm.routes.controllers;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.DTOs.PassDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.DTOs.WSUSerDto;
import com.backend.crm.routes.services.WSUSerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Пользователи")
@RestController
@RequestMapping("/wsuser")
@RequiredArgsConstructor
public class WSUSerController {
    private final WSUSerService service;

    @PostMapping("/all")
    @Operation(summary = "Получить всех пользователей")
    public Response findAllWSUSerBySort(@RequestBody SortDto sortDto){
        return this.service.findAllBySort(sortDto);
    }

    @PostMapping
    @Operation(summary = "Добавить нового пользователя")
    public Response saveWSUSer(@RequestBody WSUSerDto dto){
        return this.service.save(dto);
    }

    @PutMapping
    @Operation(summary = "Изменить пользователя")
    public Response editWSUSer(@RequestParam("id") Long id, @RequestBody WSUSerDto dto){
        return this.service.saveEdit(id, dto);
    }

    @DeleteMapping
    @Operation(summary = "Удалить пользователя")
    public Response deleteWSUSerById(@RequestParam Long id){
        return this.service.deleteById(id);
    }

    @GetMapping
    @Operation(summary = "Получить пользователя")
    public Response findWSUSerById(@RequestParam Long id){
        return this.service.findById(id);
    }
}
