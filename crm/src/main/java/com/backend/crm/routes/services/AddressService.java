package com.backend.crm.routes.services;

import com.backend.crm.app.config.Mapper;
import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.AddressDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.models.Address;
import com.backend.crm.routes.repositories.AddressRepository;
import com.backend.crm.routes.repositories.AddressSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public Response save(AddressDto dto){
        try {
            if (dto == null){
                return new Response(HttpStatus.NO_CONTENT.value(), "dto - пусто");
            }

            Address address = mapper.getMapper().map(dto, Address.class);
            address.setCreatedAt(LocalDateTime.now());

            this.repository.save(address);
            return new Response(HttpStatus.CREATED.value(), "Адрес успешно сохранен");
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Получить все адреса с сортировкой постранично
     * */

    public Response findAllBySort(SortDto dto){
        try {
            if (dto.getSort().isEmpty()){
                return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll());
            }

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

            Specification<Address> spec = AddressSpecifications.deletedAtIsNull();

            if (!dto.getSearch().isEmpty()) {
                spec = spec.and(AddressSpecifications.search(dto.getSearch()));

                return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll(spec, pageRequest).getContent());
            }

            return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll(pageRequest).getContent());
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Изменить существующий адрес
     * */

    public Response saveEdit(AddressDto dto, Long id){
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

    public Response findById(Long id){
        try {
            return new ResponseData<>(HttpStatus.OK.value(),
                    "Успешно получено",
                    this.repository.findById(id).get());
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Удалить адрес по id
     * */

    public Response deleteById(Long id){
        try {
            Optional<Address> current = this.repository.findById(id);

            if (current.isEmpty()){
                return new Response(HttpStatus.NOT_FOUND.value(), "Такого средства связи нет");
            }

            Address address = current.get();

            if (address.getDeletedAt() != null){
                address.setDeletedAt(null);
                address.setUpdatedAt(LocalDateTime.now());
            }else {
                address.setDeletedAt(LocalDateTime.now());
            }
            this.repository.save(address);
            return new Response(HttpStatus.OK.value(), "Успешно удалено/востановлено");
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