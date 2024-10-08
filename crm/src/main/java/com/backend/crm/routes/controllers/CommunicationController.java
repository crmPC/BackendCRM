package com.backend.crm.routes.controllers;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.DTOs.CommunicationDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.services.CommunicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Средсвта связи")
@RestController
@RequestMapping("/communication")
@RequiredArgsConstructor
public class CommunicationController {
    private final CommunicationService service;

    @PostMapping("/all")
    @Operation(summary = "Получить все средства связи (передается параметром)")
    public Response findAllCommunicationBySort(@RequestBody SortDto sortDto){
        return this.service.findAllBySort(sortDto);
    }

    @PostMapping
    @Operation(summary = "Добавить новую средсвто связи")
    public Response saveCommunication(@RequestBody CommunicationDto communicationDto){
        return this.service.save(communicationDto);
    }

    @PutMapping
    @Operation(summary = "Изменить средсвто связи")
    public Response editCommunication(@RequestParam("id") Long id, @RequestBody CommunicationDto communicationDto){
        return this.service.saveEdit(id, communicationDto);
    }

    @DeleteMapping
    @Operation(summary = "Удалить средство связи")
    public Response deleteCommunicationById(@RequestParam Long id){
        return this.service.deleteById(id);
    }

    @GetMapping
    @Operation(summary = "Получить средство связи")
    public Response findCommunicationById(@RequestParam Long id){
        return this.service.findById(id);
    }
}
