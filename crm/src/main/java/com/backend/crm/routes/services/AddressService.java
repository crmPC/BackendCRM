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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ## Сервис адресов
 *
 * @author Горелов Дмитрий
 * */

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository repository;

    private final Mapper mapper;

    /**
     * Создать новый адрес
     * */

    public Response saveAddress(AddressDto dto){
        try {
            if (dto == null){
                return new Response(HttpStatus.NO_CONTENT.value(), "dto - пусто");
            }

            Address address = mapper.getMapper().map(dto, Address.class);
            address.setCreatedAt(LocalDateTime.now());

            this.repository.save(address);
            return new Response(HttpStatus.OK.value(), "Адрес успешно сохранен");
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Получить все адреса с сортировкой постранично
     * */

    public Response findAllWithSort(SortDto dto){
        try {
            System.out.println(dto.getSort().getFirst().getField());
            PageRequest pageRequest;
            if (dto.getSort().getFirst().getSortDir().equals("asc")){
                pageRequest = PageRequest.of(dto.getPage()-1,
                        dto.getLimit(),
                        Sort.by(dto.getSort().getFirst().getField()).ascending());
                System.out.println("По возрастанию");
            }else {
                pageRequest = PageRequest.of(dto.getPage()-1,
                        dto.getLimit(),
                        Sort.by(dto.getSort().getFirst().getField()).descending());
                System.out.println("По убыванию");
            }

            return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll(pageRequest).getContent());
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Изменить существующий адрес
     * */

    public Response saveEditAddress(AddressDto dto, Long id){
        try {
            this.repository.save(updateAddress(dto, id));
            return new Response(HttpStatus.OK.value(), "Успешно сохранено");
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Получить адрес по id
     * */

    public Response findAddressById(Long id){
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findById(id).get());
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Удалить адрес по id
     * */

    public Response deleteAddressById(Long id){
        try {
            Address address = this.repository.findById(id).get();
            address.setDeletedAt(LocalDateTime.now());

            this.repository.save(address);
            return new Response(HttpStatus.OK.value(), "Успешно удалено");
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Вспомогательный метод вместо mapper
     * */

    private Address updateAddress(AddressDto dto, Long id){
        try {
            Address address = this.repository.findById(id).get();

            address.setCountry(dto.getCountry());
            address.setHouse(dto.getHouse());
            address.setCity(dto.getCity());
            address.setApartment(dto.getApartment());
            address.setStreet(dto.getApartment());
            address.setRegion(dto.getRegion());
            address.setZipCode(dto.getZipCode());
            address.setUpdatedAt(LocalDateTime.now());

            return address;
        }catch (Exception err){
            throw new RuntimeException(err.getMessage());
        }
    }
}