package com.backend.crm.routes.controllers;

import com.backend.crm.routes.DTOs.CommunicationDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.services.CommunicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Средсвта связи")
@RestController
@RequestMapping("/communication")
@RequiredArgsConstructor
public class CommunicationController {
    private final CommunicationService service;

    @PostMapping("/all")
    @Operation(summary = "Получить все средства связи (передается параметром)")
    public ResponseEntity findAllCommunicationBySort(@RequestHeader("Authorization") String authorization, @RequestBody SortDto sortDto){
        return this.service.findAllBySort(sortDto, authorization);
    }

    @PostMapping
    @Operation(summary = "Добавить новую средсвто связи")
    public ResponseEntity saveCommunication(@RequestHeader("Authorization") String authorization, @RequestBody CommunicationDto communicationDto){
        return this.service.save(communicationDto, authorization);
    }

    @PutMapping
    @Operation(summary = "Изменить средсвто связи")
    public ResponseEntity editCommunication(@RequestHeader("Authorization") String authorization, @RequestParam("id") Long id, @RequestBody CommunicationDto communicationDto){
        return this.service.saveEdit(id, communicationDto, authorization);
    }

    @DeleteMapping
    @Operation(summary = "Удалить средство связи")
    public ResponseEntity deleteCommunicationById(@RequestHeader("Authorization") String authorization, @RequestParam Long id){
        return this.service.deleteById(id, authorization);
    }

    @GetMapping
    @Operation(summary = "Получить средство связи")
    public ResponseEntity findCommunicationById(@RequestHeader("Authorization") String authorization, @RequestParam Long id){
        return this.service.findById(id, authorization);
    }
}
