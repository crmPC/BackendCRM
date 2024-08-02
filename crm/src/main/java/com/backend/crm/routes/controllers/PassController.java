package com.backend.crm.routes.controllers;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.DTOs.PassDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.services.PassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Сервис пропусков")
@RestController
@RequestMapping("/pass")
@RequiredArgsConstructor
public class PassController {
    private final PassService service;

    @PostMapping("/all")
    @Operation(summary = "Получить все пропуска")
    public Response findAllPassBySort(@RequestBody SortDto sortDto){
        return this.service.findAllBySort(sortDto);
    }

    @PostMapping
    @Operation(summary = "Добавить новый пропуск")
    public Response savePass(@RequestBody PassDto number){
        return this.service.save(number.getNumber());
    }

    @PutMapping
    @Operation(summary = "Изменить пропуск")
    public Response editPass(@RequestParam("id") Long id, @RequestBody PassDto number){
        return this.service.saveEdit(id, number.getNumber());
    }

    @DeleteMapping
    @Operation(summary = "Удалить пропуск")
    public Response deletePAssById(@RequestParam Long id){
        return this.service.deleteById(id);
    }

    @GetMapping
    @Operation(summary = "Получить пропуск")
    public Response findPassById(@RequestParam Long id){
        return this.service.findById(id);
    }
}
