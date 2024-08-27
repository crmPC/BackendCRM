package com.backend.crm.routes.controllers;


import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.DTOs.CompanyDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.services.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Компании")
@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService service;

    @PostMapping("/all")
    @Operation(summary = "Получить все компании")
    public Response findAllCompanyBySort(@RequestBody SortDto sortDto){
        return this.service.findAllBySort(sortDto);
    }

    @PostMapping
    @Operation(summary = "Добавить новую компанию")
    public Response saveCompany(@RequestBody CompanyDto companyDto){
        return this.service.save(companyDto);
    }

    @PutMapping
    @Operation(summary = "Изменить компанию")
    public Response editCompany(@RequestParam("id") Long id, @RequestBody CompanyDto companyDto){
        return this.service.saveEdit(id, companyDto);
    }

    @DeleteMapping
    @Operation(summary = "Удалить компанию")
    public Response deleteCompanyById(@RequestParam Long id){
        return this.service.deleteById(id);
    }

    @GetMapping
    @Operation(summary = "Получить компанию")
    public Response findCompanyById(@RequestParam Long id){
        return this.service.findById(id);
    }
}
