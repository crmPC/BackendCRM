package com.backend.crm.routes.services;

import com.backend.crm.app.config.Mapper;
import com.backend.crm.app.domain.ValidateService;
import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.AllowedIpDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.models.AllowedIp;
import com.backend.crm.routes.models.UserEntity;
import com.backend.crm.routes.models.UserRole;
import com.backend.crm.routes.repositories.AllowedIpRepository;
import com.backend.crm.routes.repositories.AllowedIpSpecifications;
import lombok.RequiredArgsConstructor;
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
 * ## Сервис IP LDAP
 *
 * @author Горелов Дмитрий
 */

@Service
@RequiredArgsConstructor
public class AllowedIpService {
    private final AllowedIpRepository repository;

    private final Mapper mapper;

    private final ValidateService authService;

    /**
     * Получить вcе IP LDAP
     */

    public ResponseEntity findAllBySort(SortDto dto, String token) {
        try {
            if (dto.getSort().isEmpty()){
                return new ResponseEntity(this.repository.findAll(), HttpStatus.NOT_FOUND);
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

            if (dto.getSort().getFirst().getSortDir().equals("asc")) {
                pageRequest = PageRequest.of(dto.getPage() - 1,
                        dto.getLimit(),
                        Sort.by(dto.getSort().getFirst().getField()).ascending());
            } else {
                pageRequest = PageRequest.of(dto.getPage() - 1,
                        dto.getLimit(),
                        Sort.by(dto.getSort().getFirst().getField()).descending());
            }

            Specification<AllowedIp> spec = AllowedIpSpecifications.deletedAtIsNull();

            if (!dto.getSearch().isEmpty()) {
                spec = spec.and(AllowedIpSpecifications.search(dto.getSearch()));

                return new ResponseEntity(this.repository.findAll(spec, pageRequest).getContent(), HttpStatus.OK);
            }

            return new ResponseEntity(this.repository.findAll(pageRequest).getContent(), HttpStatus.OK);
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Добавить IP LDAP
     */

    public ResponseEntity save(AllowedIpDto dto, String token) {
        try {
            //Работа с токеном
            UserEntity user = authService.validateTokenByToken(token);

            if (user.equals(null)){
                return new ResponseEntity("Время действия токена истекло", HttpStatus.UNAUTHORIZED);
            }

            if (!authService.checkAccess(Arrays.asList(UserRole.super_admin, UserRole.moderator), user)) {
                return new ResponseEntity("Нет доступа на выоление запроса", HttpStatus.FORBIDDEN);
            }

            //Выполнение запроса

            AllowedIp allowedIp = mapper.getMapper().map(dto, AllowedIp.class);
            allowedIp.setCreatedAt(LocalDateTime.now());

            System.out.println(allowedIp);
            this.repository.save(allowedIp);
            return new ResponseEntity("Успешно сохранено", HttpStatus.CREATED);
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Изменить IP LDAP
     */

    public ResponseEntity saveEdit(Long id, AllowedIpDto dto, String token) {
        try {
            //Работа с токеном
            UserEntity user = authService.validateTokenByToken(token);

            if (user.equals(null)){
                return new ResponseEntity("Время действия токена истекло", HttpStatus.UNAUTHORIZED);
            }

            if (!authService.checkAccess(Arrays.asList(UserRole.super_admin, UserRole.moderator), user)) {
                return new ResponseEntity("Нет доступа на выоление запроса", HttpStatus.FORBIDDEN);
            }

            //Выполнение запроса

            Optional<AllowedIp> current = this.repository.findById(id);

            if (current.isEmpty()) {
                return new ResponseEntity("Такой группы пользователей нет", HttpStatus.NOT_FOUND);
            }

            AllowedIp allowedIp = current.get();
            mapper.getMapper().map(dto, allowedIp);
            allowedIp.setUpdatedAt(LocalDateTime.now());

            this.repository.save(allowedIp);
            return new ResponseEntity("Успешно сохранено", HttpStatus.OK);
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Удалить IP LDAP
     */

    public ResponseEntity deleteById(Long id, String token){
        try {
            //Работа с токеном
            UserEntity user = authService.validateTokenByToken(token);

            if (user.equals(null)){
                return new ResponseEntity("Время действия токена истекло", HttpStatus.UNAUTHORIZED);
            }

            if (!authService.checkAccess(Arrays.asList(UserRole.super_admin, UserRole.moderator), user)) {
                return new ResponseEntity("Нет доступа на выоление запроса", HttpStatus.FORBIDDEN);
            }

            //Выполнение запроса

            Optional<AllowedIp> current = this.repository.findById(id);

            if (current.isEmpty()){
                return new ResponseEntity("Такой группы пользователей нет", HttpStatus.NOT_FOUND);
            }

            AllowedIp allowedIp = current.get();

            if (allowedIp.getDeletedAt() != null){
                allowedIp.setDeletedAt(null);
                allowedIp.setUpdatedAt(LocalDateTime.now());
            }else {
                allowedIp.setDeletedAt(LocalDateTime.now());
            }

            this.repository.save(allowedIp);
            return new ResponseEntity("Успешно удалено/востановлено", HttpStatus.OK);
        }catch (Exception err){
            System.out.println(err.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Получить IP LDAP
     */

    public ResponseEntity findById(Long id, String token){
        try {
            //Работа с токеном
            UserEntity user = authService.validateTokenByToken(token);

            if (user.equals(null)){
                return new ResponseEntity("Время действия токена истекло", HttpStatus.UNAUTHORIZED);
            }

            if (!authService.checkAccess(Arrays.asList(UserRole.super_admin, UserRole.moderator), user)) {
                return new ResponseEntity("Нет доступа на выоление запроса", HttpStatus.FORBIDDEN);
            }

            //Выполнение запроса

            return new ResponseEntity(this.repository.findById(id).get(), HttpStatus.OK);
        }catch (Exception err){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

}
