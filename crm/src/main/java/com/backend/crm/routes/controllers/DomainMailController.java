package com.backend.crm.routes.controllers;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.DTOs.DomainMailDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.services.DomainMailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
