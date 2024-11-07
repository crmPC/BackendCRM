package com.backend.crm.routes.controllers;

import com.backend.crm.routes.DTOs.AllowedIpDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.services.AllowedIpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "IP")
@RestController
@RequestMapping("allowedip")
@RequiredArgsConstructor
public class AllowedIpController {

    private final AllowedIpService service;

    @PostMapping("/all")
    @Operation(summary = "Получить ip")
    public ResponseEntity findAllIPBySort(@RequestHeader("Authorization") String authorization, @RequestBody SortDto sortDto){
        return this.service.findAllBySort(sortDto, authorization);
    }

    @PostMapping
    @Operation(summary = "Добавить ip")
    public ResponseEntity saveIP(@RequestHeader("Authorization") String authorization, @RequestBody AllowedIpDto dto){
        return this.service.save(dto, authorization);
    }

    @PutMapping
    @Operation(summary = "Изменить ip")
    public ResponseEntity editIP(@RequestHeader("Authorization") String authorization, @RequestParam("id") Long id, @RequestBody AllowedIpDto dto){
        return this.service.saveEdit(id, dto, authorization);
    }

    @DeleteMapping
    @Operation(summary = "Удалить ip")
    public ResponseEntity deleteIPById(@RequestHeader("Authorization") String authorization, @RequestParam Long id){
        return this.service.deleteById(id, authorization);
    }

    @GetMapping
    @Operation(summary = "Получить ip")
    public ResponseEntity findIPById(@RequestHeader("Authorization") String authorization, @RequestParam Long id){
        return this.service.findById(id, authorization);
    }
}
