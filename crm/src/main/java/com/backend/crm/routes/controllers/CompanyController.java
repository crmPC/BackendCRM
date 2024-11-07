package com.backend.crm.routes.controllers;

import com.backend.crm.routes.DTOs.CompanyDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.services.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Компании")
@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService service;

    @PostMapping("/all")
    @Operation(summary = "Получить все компании")
    public ResponseEntity findAllCompanyBySort(@RequestHeader("Authorization") String authorization, @RequestBody SortDto sortDto){
        return this.service.findAllBySort(sortDto, authorization);
    }

    @PostMapping
    @Operation(summary = "Добавить новую компанию")
    public ResponseEntity saveCompany(@RequestHeader("Authorization") String authorization, @RequestBody CompanyDto companyDto){
        return this.service.save(companyDto, authorization);
    }

    @PutMapping
    @Operation(summary = "Изменить компанию")
    public ResponseEntity editCompany(@RequestHeader("Authorization") String authorization, @RequestParam("id") Long id, @RequestBody CompanyDto companyDto){
        return this.service.saveEdit(id, companyDto, authorization);
    }

    @DeleteMapping
    @Operation(summary = "Удалить компанию")
    public ResponseEntity deleteCompanyById(@RequestHeader("Authorization") String authorization, @RequestParam Long id){
        return this.service.deleteById(id, authorization);
    }

    @GetMapping
    @Operation(summary = "Получить компанию")
    public ResponseEntity findCompanyById(@RequestHeader("Authorization") String authorization, @RequestParam Long id){
        return this.service.findById(id, authorization);
    }
}
