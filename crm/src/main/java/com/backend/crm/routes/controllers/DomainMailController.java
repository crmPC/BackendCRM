package com.backend.crm.routes.controllers;

import com.backend.crm.routes.DTOs.DomainMailDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.services.DomainMailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Сервис почты домена")
@RestController
@RequestMapping("/domainmail")
@RequiredArgsConstructor
public class DomainMailController {
    private final DomainMailService service;

    @PostMapping("/all")
    @Operation(summary = "Получить все почты")
    public ResponseEntity findAllDomainMailBySort(@RequestHeader("Authorization") String authorization, @RequestBody SortDto sortDto){
        return this.service.findAllBySort(sortDto, authorization);
    }

    @PostMapping
    @Operation(summary = "Добавить новую почту")
    public ResponseEntity saveDomainMail(@RequestHeader("Authorization") String authorization, @RequestBody DomainMailDto domainMailDto){
        return this.service.save(domainMailDto, authorization);
    }

    @PutMapping
    @Operation(summary = "Изменить почту")
    public ResponseEntity editDomainMail(@RequestHeader("Authorization") String authorization, @RequestParam("id") Long id, @RequestBody DomainMailDto domainMailDto){
        return this.service.saveEdit(id, domainMailDto, authorization);
    }

    @DeleteMapping
    @Operation(summary = "Удалить почту")
    public ResponseEntity deleteDomainMailById(@RequestHeader("Authorization") String authorization, @RequestParam Long id){
        return this.service.deleteById(id, authorization);
    }

    @GetMapping
    @Operation(summary = "Получить почту")
    public ResponseEntity findDomainMailById(@RequestHeader("Authorization") String authorization, @RequestParam Long id){
        return this.service.findById(id, authorization);
    }
}
