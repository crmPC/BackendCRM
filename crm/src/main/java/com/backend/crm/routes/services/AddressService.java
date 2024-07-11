package com.backend.crm.routes.services;

import com.backend.crm.app.config.Mapper;
import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.AddressDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.models.Address;
import com.backend.crm.routes.repositories.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository repository;

    private final Mapper mapper;

    public Response saveAddress(AddressDto dto){
        try {
            System.out.println(dto.toString());
            if (dto == null){
                return new Response(HttpStatus.NO_CONTENT.value(), "dto - пусто");
            }

            this.repository.save(mapper.getMapper().map(dto, Address.class));
            return new Response(HttpStatus.OK.value(), "Адрес успешно сохранен");
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    public Response findAllWithSort(SortDto dto){
        try {
            System.out.println(dto.toString());
            PageRequest pageRequest = PageRequest.of(dto.getPage()-1, dto.getLimit());
            return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAllByOrderByIdAddressAsc(pageRequest));


        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }
}
