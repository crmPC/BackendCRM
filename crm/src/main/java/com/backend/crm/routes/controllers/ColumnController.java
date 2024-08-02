package com.backend.crm.routes.controllers;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.routes.services.ColumnService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Column сервис")
@RestController
@RequestMapping("/columns")
@RequiredArgsConstructor
public class ColumnController {
    private final ColumnService service;

    @GetMapping
    public Response findAllColumn(){
        return this.service.findAll();
    }

    @PostMapping
    public Response saveColumn(@RequestBody Object object, @RequestHeader String Authorization){
        System.out.println(object.toString());
        System.out.println(Authorization);
        return this.service.saveColumn(object);
    }
}
