package com.backend.crm.routes.services;

import com.backend.crm.app.config.Mapper;
import com.backend.crm.app.domain.ValidateService;
import com.backend.crm.routes.DTOs.CompanyDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.models.Company;
import com.backend.crm.routes.models.UserEntity;
import com.backend.crm.routes.models.UserRole;
import com.backend.crm.routes.repositories.CompanyRepository;
import com.backend.crm.routes.repositories.CompanySpecifications;
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
 * ## Сервис компании
 *
 * @author Горелов Дмитрий
 */

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository repository;

    private final Mapper mapper;

    private final ValidateService authService;

    /**
     * Получить все компании
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

            Specification<Company> spec = CompanySpecifications.deletedAtIsNull();

            if (!dto.getSearch().isEmpty()) {
                spec = spec.and(CompanySpecifications.search(dto.getSearch()));

                return new ResponseEntity(this.repository.findAll(pageRequest).getContent(), HttpStatus.OK);
            }

            return new ResponseEntity(this.repository.findAll(pageRequest).getContent(), HttpStatus.OK);
        } catch (Exception err) {
            System.out.println(err.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Добавить новую компанию
     */

    public ResponseEntity save(CompanyDto dto, String token){
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

            Company company = mapper.getMapper().map(dto, Company.class);
            company.setCreatedAt(LocalDateTime.now());

            this.repository.save(company);
            return new ResponseEntity("Успешно сохранено", HttpStatus.CREATED);
        }catch (Exception err){
            System.out.println(err.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Изменить компанию
     */

    public ResponseEntity saveEdit(Long id, CompanyDto dto, String token) {
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

            Optional<Company> current = this.repository.findById(id);

            if (current.isEmpty()) {
                return new ResponseEntity("Такого Компании не существует", HttpStatus.NOT_FOUND);
            }

            Company company = editCompany(current.get(), dto);
            company.setUpdatedAt(LocalDateTime.now());

            this.repository.save(company);
            return new ResponseEntity("Успешно сохранено", HttpStatus.OK);
        } catch (Exception err) {
            System.out.println(err.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Удалить компанию
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

            Optional<Company> current = this.repository.findById(id);

            if (current.isEmpty()){
                return new ResponseEntity("Такого Компании не существует", HttpStatus.NOT_FOUND);
            }

            Company company = current.get();

            if (company.getDeletedAt() != null){
                company.setDeletedAt(null);
                company.setUpdatedAt(LocalDateTime.now());
            }else {
                company.setDeletedAt(LocalDateTime.now());
            }

            this.repository.save(company);
            return new ResponseEntity("Успешно удалено/востановлено", HttpStatus.OK);
        }catch (Exception err){
            System.out.println(err.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Получить компанию
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

    private Company editCompany(Company company, CompanyDto dto){
        company.setAddress(dto.getAddress());
        company.setContact(dto.getContact());
        company.setContactuser(dto.getContactuser());
        company.setDescription(dto.getDescription());
        company.setFormat(dto.getFormat());
        company.setInn(dto.getInn());
        company.setDomainAd(dto.getDomainAd());
        company.setEmail_domain(dto.getEmail_domain());
        company.setOfficeequip(dto.getOfficeequip());
        company.setPrefix(dto.getPrefix());

        return company;
    }
}
