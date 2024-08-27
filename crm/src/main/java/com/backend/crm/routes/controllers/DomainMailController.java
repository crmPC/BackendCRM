package com.backend.crm.routes.controllers;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.DTOs.DomainMailDto;
import com.backend.crm.routes.DTOs.PassDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.services.DomainMailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Сервис почты домена")
@RestController
@RequestMapping("/domainmail")
@RequiredArgsConstructor
public class DomainMailController {
    private final DomainMailService service;

    @PostMapping("/all")
    @Operation(summary = "Получить все почты")
    public Response findAllDomainMailBySort(@RequestBody SortDto sortDto){
        return this.service.findAllBySort(sortDto);
    }

    @PostMapping
    @Operation(summary = "Добавить новую почту")
    public Response saveDomainMail(@RequestBody DomainMailDto domainMailDto){
        return this.service.save(domainMailDto);
    }

    @PutMapping
    @Operation(summary = "Изменить почту")
    public Response editDomainMail(@RequestParam("id") Long id, @RequestBody DomainMailDto domainMailDto){
        return this.service.saveEdit(id, domainMailDto);
    }

    @DeleteMapping
    @Operation(summary = "Удалить почту")
    public Response deleteDomainMailById(@RequestParam Long id){
        return this.service.deleteById(id);
    }

    @GetMapping
    @Operation(summary = "Получить почту")
    public Response findDomainMailById(@RequestParam Long id){
        return this.service.findById(id);
    }
}
