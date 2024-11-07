package com.backend.crm.routes.services;

import com.backend.crm.app.config.Mapper;
import com.backend.crm.app.domain.ValidateService;
import com.backend.crm.routes.DTOs.AddressDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.models.Address;
import com.backend.crm.routes.models.UserEntity;
import com.backend.crm.routes.models.UserRole;
import com.backend.crm.routes.repositories.AddressRepository;
import com.backend.crm.routes.repositories.AddressSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
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

    private final ValidateService authService;

    /**
     * Создать новый адрес
     * */

    public ResponseEntity save(AddressDto dto, String token){
        try {
            if (dto == null){
                return new ResponseEntity("dto - пусто", HttpStatus.NO_CONTENT);
            }

            //Работа с токеном
            UserEntity user = authService.validateTokenByToken(token);

            if (user.equals(null)){
                return new ResponseEntity("Время действия токена истекло", HttpStatus.UNAUTHORIZED);
            }

            if (!authService.checkAccess(Arrays.asList(UserRole.super_admin, UserRole.admin, UserRole.moderator), user)) {
                return new ResponseEntity("Нет доступа на выоление запроса", HttpStatus.FORBIDDEN);
            }

            //Выполнение запроса

            Address address = mapper.getMapper().map(dto, Address.class);
            address.setCreatedAt(LocalDateTime.now());

            this.repository.save(address);
            return new ResponseEntity("Адрес успешно сохранен", HttpStatus.CREATED);
        }catch (Exception err){
            return new ResponseEntity(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Получить все адреса с сортировкой постранично
     * */

    public ResponseEntity findAllBySort(SortDto dto, String token){
        try {
            if (dto.getSort().isEmpty()){
                return new ResponseEntity(this.repository.findAll(), HttpStatus.OK);
            }

            //Работа с токеном
            UserEntity user = authService.validateTokenByToken(token);

            if (user.equals(null)){
                return new ResponseEntity("Время действия токена истекло", HttpStatus.UNAUTHORIZED);
            }

            if (!authService.checkAccess(Arrays.asList(UserRole.super_admin, UserRole.admin, UserRole.moderator), user)) {
                return new ResponseEntity("Нет доступа на выоление запроса", HttpStatus.FORBIDDEN);
            }

            //Выполнение запроса

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

                return new ResponseEntity(this.repository.findAll(pageRequest).getContent(), HttpStatus.OK);
            }

            return new ResponseEntity(this.repository.findAll(pageRequest).getContent(), HttpStatus.OK);
        }catch (Exception err){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Изменить существующий адрес
     * */

    public ResponseEntity saveEdit(AddressDto dto, Long id, String token){
        try {
            //Работа с токеном
            UserEntity user = authService.validateTokenByToken(token);

            if (user.equals(null)){
                return new ResponseEntity("Время действия токена истекло", HttpStatus.UNAUTHORIZED);
            }

            if (!authService.checkAccess(Arrays.asList(UserRole.super_admin, UserRole.admin, UserRole.moderator), user)) {
                return new ResponseEntity("Нет доступа на выоление запроса", HttpStatus.FORBIDDEN);
            }

            //Выполнение запроса

            this.repository.save(updateAddress(dto, id));
            return new ResponseEntity("Успешно сохранено", HttpStatus.OK);
        }catch (Exception err){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Получить адрес по id
     * */

    public ResponseEntity findById(Long id, String token){
        try {
            //Работа с токеном
            UserEntity user = authService.validateTokenByToken(token);

            if (user.equals(null)){
                return new ResponseEntity("Время действия токена истекло", HttpStatus.UNAUTHORIZED);
            }

            if (!authService.checkAccess(Arrays.asList(UserRole.super_admin, UserRole.admin, UserRole.moderator), user)) {
                return new ResponseEntity("Нет доступа на выоление запроса", HttpStatus.FORBIDDEN);
            }

            //Выполнение запроса

            return new ResponseEntity(this.repository.findById(id).get(), HttpStatus.OK);
        }catch (Exception err){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Удалить адрес по id
     * */

    public ResponseEntity deleteById(Long id, String token){
        try {
            //Работа с токеном
            UserEntity user = authService.validateTokenByToken(token);

            if (user.equals(null)){
                return new ResponseEntity("Время действия токена истекло", HttpStatus.UNAUTHORIZED);
            }

            if (!authService.checkAccess(Arrays.asList(UserRole.super_admin, UserRole.admin, UserRole.moderator), user)) {
                return new ResponseEntity("Нет доступа на выоление запроса", HttpStatus.FORBIDDEN);
            }

            //Выполнение запроса

            Optional<Address> current = this.repository.findById(id);

            if (current.isEmpty()){
                return new ResponseEntity("Такого средства связи нет", HttpStatus.NOT_FOUND);
            }

            Address address = current.get();

            if (address.getDeletedAt() != null){
                address.setDeletedAt(null);
                address.setUpdatedAt(LocalDateTime.now());
            }else {
                address.setDeletedAt(LocalDateTime.now());
            }
            this.repository.save(address);
            return new ResponseEntity("Успешно удалено/востановлено", HttpStatus.OK);
        }catch (Exception err){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
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