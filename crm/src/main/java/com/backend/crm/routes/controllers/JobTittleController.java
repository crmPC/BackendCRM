package com.backend.crm.routes.controllers;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.DTOs.JobTittleDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.services.JobTittleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Сервис должностей")
@RestController
@RequestMapping("/jobtitle")
@RequiredArgsConstructor
public class JobTittleController {
    private final JobTittleService service;

    @PostMapping("/all")
    @Operation(summary = "Получить все должности")
    public Response findAllJobTittleBySort(@RequestBody SortDto sortDto){
        return this.service.findAllBySort(sortDto);
    }

    @PostMapping
    @Operation(summary = "Добавить новую должность")
    public Response saveJobTittle(@RequestBody JobTittleDto jobTittleDto){
        return this.service.save(jobTittleDto);
    }

    @PutMapping
    @Operation(summary = "Изменить должность")
    public Response editJobTittle(@RequestParam("id") Long id, @RequestBody JobTittleDto jobTittleDto){
        return this.service.saveEdit(id, jobTittleDto);
    }

    @DeleteMapping
    @Operation(summary = "Удалить должность")
    public Response deleteJobTittleById(@RequestParam Long id){
        return this.service.deleteById(id);
    }

    @GetMapping
    @Operation(summary = "Получить должность")
    public Response findCommunicationById(@RequestParam Long id){
        return this.service.findById(id);
    }
}
