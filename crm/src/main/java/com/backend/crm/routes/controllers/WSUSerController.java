package com.backend.crm.routes.controllers;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.services.WSUSerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Пользователи")
@RestController
@RequestMapping("/wsuser")
@RequiredArgsConstructor
public class WSUSerController {
    private final WSUSerService service;

    @PostMapping("/all")
    @Operation(summary = "Получить всех пользователей")
    public Response findAllCommunicationBySort(@RequestBody SortDto sortDto){
        return this.service.findAllWSUSerBySort(sortDto);
    }
}
