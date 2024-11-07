package com.backend.crm.routes.services;

import com.backend.crm.app.config.Mapper;
import com.backend.crm.app.domain.TokenService;
import com.backend.crm.app.domain.ValidateService;
import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.AccessUsersCompanyDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.models.*;
import com.backend.crm.routes.repositories.AccessUsersCompanyRepository;
import com.backend.crm.routes.repositories.WSUSerSpecifications;
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

@Service
@AllArgsConstructor
public class AccessUsersCompanyService {

    private final AccessUsersCompanyRepository repository;

    private final Mapper mapper;

    private final ValidateService authService;

    /**
     * Получить всех адиминов компании
     */

    public ResponseEntity findAllBySort(SortDto dto, String token) {
        try {
            //Работа с токеном
            UserEntity user = authService.validateTokenByToken(token);

            if (user.equals(null)){
                return new ResponseEntity("Время действия токена истекло", HttpStatus.UNAUTHORIZED);
            }

            if (!authService.checkAccess(Arrays.asList(UserRole.super_admin, UserRole.admin), user)) {
                return new ResponseEntity("Нет доступа на выоление запроса", HttpStatus.FORBIDDEN);
            }

            //Выполнение запроса

            if (dto.getSort().isEmpty()){
                return new ResponseEntity(this.repository.findAll(), HttpStatus.OK);
            }

            PageRequest pageRequest;

            if (dto.getSort().getFirst().getSortDir().equals("asc")) {
                pageRequest = PageRequest.of(dto.getPage() - 1,
                        dto.getLimit(),
                        Sort.by(dto.getSort().getFirst().getField()).ascending());
            } else {
                pageRequest = PageRequest.of(dto.getPage() - 1,
                        dto.getLimit(),
                        Sort.by(dto.getSort().getFirst().getField()).descending());
            }

            Specification<WSUSer> spec = WSUSerSpecifications.deletedAtIsNull();

            if (!dto.getSearch().isEmpty()) {
                spec = spec.and(WSUSerSpecifications.search(dto.getSearch()));

                return new ResponseEntity(this.repository.findAll(pageRequest).getContent(), HttpStatus.OK);
            }

            return new ResponseEntity(this.repository.findAll(pageRequest).getContent(), HttpStatus.OK);
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Добавить нового админа компании
     */

    public ResponseEntity save(AccessUsersCompanyDto dto, String token) {
        try {
            //Работа с токеном
            UserEntity user = authService.validateTokenByToken(token);

            if (user.equals(null)){
                return new ResponseEntity("Время действия токена истекло", HttpStatus.UNAUTHORIZED);
            }

            if (!authService.checkAccess(Arrays.asList(UserRole.super_admin), user)) {
                return new ResponseEntity("Нет доступа на выоление запроса", HttpStatus.FORBIDDEN);
            }

            //Выполнение запроса

            AccessUsersCompany accessUsersCompany = mapper.getMapper().map(dto, AccessUsersCompany.class);
            accessUsersCompany.setCreatedAt(LocalDateTime.now());

            this.repository.save(accessUsersCompany);
            return new ResponseEntity("Успешно сохранено", HttpStatus.CREATED);
        } catch (Exception err) {
            return new ResponseEntity(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Изменить админа компании
     */

    public ResponseEntity saveEdit(Long id, AccessUsersCompanyDto dto, String token) {
        try {
            //Работа с токеном
            UserEntity user = authService.validateTokenByToken(token);

            if (user.equals(null)){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(HttpStatus.UNAUTHORIZED.value(), "Время действия токена истекло"));
            }

            if (!authService.checkAccess(Arrays.asList(UserRole.super_admin), user)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response(HttpStatus.FORBIDDEN.value(), "Нет доступа на выоление запроса"));
            }

            //Выполнение запроса

            Optional<AccessUsersCompany> current = this.repository.findById(id);

            if (current.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(HttpStatus.NOT_FOUND.value(), "Такого пользователя нет"));
            }

            AccessUsersCompany accessUsersCompany = current.get();
            mapper.getMapper().map(dto, AccessUsersCompany.class);
            accessUsersCompany.setUpdatedAt(LocalDateTime.now());

            this.repository.save(accessUsersCompany);
            return ResponseEntity.status(HttpStatus.OK).body(new Response(HttpStatus.OK.value(), "Успешно сохранено"));
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage()));
        }
    }

    /**
     * Удалить админа компании
     */

    public ResponseEntity deleteById(Long id, String token){
        try {
            //Работа с токеном
            UserEntity user = authService.validateTokenByToken(token);

            if (user.equals(null)){
                return new ResponseEntity("Время действия токена истекло", HttpStatus.UNAUTHORIZED);
            }

            if (!authService.checkAccess(Arrays.asList(UserRole.super_admin), user)) {
                return new ResponseEntity("Нет доступа на выоление запроса", HttpStatus.FORBIDDEN);
            }

            //Выполнение запроса

            Optional<AccessUsersCompany> current = this.repository.findById(id);

            if (current.isEmpty()){
                return new ResponseEntity("Такого средства связи нет", HttpStatus.NOT_FOUND);
            }

            AccessUsersCompany accessUsersCompany = current.get();

            if (accessUsersCompany.getDeletedAt() != null){
                accessUsersCompany.setDeletedAt(null);
                accessUsersCompany.setUpdatedAt(LocalDateTime.now());
            }else {
                accessUsersCompany.setDeletedAt(LocalDateTime.now());
            }
            this.repository.save(accessUsersCompany);
            return new ResponseEntity("Успешно удалено/востановлено", HttpStatus.OK);
        }catch (Exception err){
            return new ResponseEntity(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Получить админа компании
     */

    public ResponseEntity findById(Long id, String token){
        try {
            //Работа с токеном
            UserEntity user = authService.validateTokenByToken(token);

            if (user.equals(null)){
                return new ResponseEntity("Время действия токена истекло", HttpStatus.UNAUTHORIZED);
            }

            if (!authService.checkAccess(Arrays.asList(UserRole.super_admin), user)) {
                return new ResponseEntity("Нет доступа на выоление запроса", HttpStatus.FORBIDDEN);
            }

            //Выполнение запроса

            return new ResponseEntity(this.repository.findById(id).get(), HttpStatus.OK);
        }catch (Exception err){
            return new ResponseEntity(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
