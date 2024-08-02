package com.backend.crm.routes.services;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.models.Columns;
import com.backend.crm.routes.repositories.ColumnRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColumnService {
    private final ColumnRepository repository;

    public Response findAll(){
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll());
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    public Response saveColumn(Object object){
        try {
            Columns columns = new Columns();
            columns.setColumns(object.toString());

            this.repository.save(columns);
            return new Response(HttpStatus.CREATED.value(), "Успешно сохранено");
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }
}
