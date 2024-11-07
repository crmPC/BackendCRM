package com.backend.crm.routes.services;

import com.backend.crm.app.domain.ValidateService;
import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.DomainMailDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.models.DomainMail;
import com.backend.crm.routes.models.UserEntity;
import com.backend.crm.routes.models.UserRole;
import com.backend.crm.routes.repositories.DomainMailRepository;
import com.backend.crm.routes.repositories.DomainMailSpecifications;
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
 * ## Сервис почты домена
 *
 * @author Горелов Дмитрий
 */

@Service
@RequiredArgsConstructor
public class DomainMailService {
    private final DomainMailRepository repository;

    private final ValidateService authService;

    /**
     * Получить все почты
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

            if (!authService.checkAccess(Arrays.asList(UserRole.super_admin, UserRole.moderator, UserRole.admin), user)) {
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

            Specification<DomainMail> spec = DomainMailSpecifications.deletedAtIsNull();

            if (!dto.getSearch().isEmpty()) {
                spec = spec.and(DomainMailSpecifications.search(dto.getSearch()));

                return new ResponseEntity(this.repository.findAll(pageRequest).getContent(), HttpStatus.OK);
            }

            return new ResponseEntity(this.repository.findAll(pageRequest).getContent(), HttpStatus.OK);
        } catch (Exception err) {
            System.out.println(err.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Добавить новую почту
     */

    public ResponseEntity save(DomainMailDto dto, String token){
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

            DomainMail domainMail = new DomainMail();
            domainMail.setCompany(dto.getCompany());
            domainMail.setName(dto.getName());
            domainMail.setCreatedAt(LocalDateTime.now());

            this.repository.save(domainMail);
            return new ResponseEntity("Успешно сохранено", HttpStatus.OK);
        }catch (Exception err){
            System.out.println(err.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Изменить почту
     */

    public ResponseEntity saveEdit(Long id, DomainMailDto dto, String token) {
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

            Optional<DomainMail> current = this.repository.findById(id);

            if (current.isEmpty()) {
                return new ResponseEntity("Такой почты не существует", HttpStatus.NOT_FOUND);
            }

            DomainMail domainMail = current.get();
            domainMail.setCompany(dto.getCompany());
            domainMail.setName(dto.getName());
            domainMail.setUpdatedAt(LocalDateTime.now());

            this.repository.save(domainMail);
            return new ResponseEntity("Успешно сохранено", HttpStatus.OK);
        } catch (Exception err) {
            System.out.println(err.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Удалить почту
     */

    public ResponseEntity deleteById(Long id, String token){
        try {
            Optional<DomainMail> current = this.repository.findById(id);

            if (current.isEmpty()){
                return new ResponseEntity("Такой почты не существует", HttpStatus.NOT_FOUND);
            }

            DomainMail domainMail = current.get();

            if (domainMail.getDeletedAt() != null){
                domainMail.setDeletedAt(null);
                domainMail.setUpdatedAt(LocalDateTime.now());
            }else {
                domainMail.setDeletedAt(LocalDateTime.now());
            }

            this.repository.save(domainMail);
            return new ResponseEntity("Успешно удалено/востановлено", HttpStatus.OK);
        }catch (Exception err){
            System.out.println(err.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Получить почту
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
