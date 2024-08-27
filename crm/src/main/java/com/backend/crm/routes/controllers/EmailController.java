package com.backend.crm.routes.controllers;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.DTOs.CompanyDto;
import com.backend.crm.routes.DTOs.EmailDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.services.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Сервис почт")
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService service;

    @PostMapping("/all")
    @Operation(summary = "Получить все почты")
    public Response findAllEmailBySort(@RequestBody SortDto sortDto){
        return this.service.findAllBySort(sortDto);
    }

    @PostMapping
    @Operation(summary = "Добавить новую почту")
    public Response saveEmail(@RequestBody EmailDto emailDto){
        return this.service.save(emailDto);
    }

    @PutMapping
    @Operation(summary = "Изменить почту")
    public Response editEmail(@RequestParam("id") Long id, @RequestBody EmailDto emailDto){
        return this.service.saveEdit(id, emailDto);
    }

    @DeleteMapping
    @Operation(summary = "Удалить почту")
    public Response deleteEmailById(@RequestParam Long id){
        return this.service.deleteById(id);
    }

    @GetMapping
    @Operation(summary = "Получить почту")
    public Response findEmailById(@RequestParam Long id){
        return this.service.findById(id);
    }
}
