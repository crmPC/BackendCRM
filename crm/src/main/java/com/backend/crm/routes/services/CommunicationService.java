package com.backend.crm.routes.services;

import com.backend.crm.app.domain.ValidateService;
import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.CommunicationDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.models.Communication;
import com.backend.crm.routes.models.UserEntity;
import com.backend.crm.routes.models.UserRole;
import com.backend.crm.routes.repositories.AddressSpecifications;
import com.backend.crm.routes.repositories.CommunicationRepository;
import com.backend.crm.routes.repositories.CommunicationSpecifications;
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
 * ## Сервис комуникации
 *
 * @author Горелов Дмитрий
 */

@Service
@RequiredArgsConstructor
public class CommunicationService {
    private final CommunicationRepository repository;

    private final ValidateService authService;

    /**
     * Получить все средства связи
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

            Specification<Communication> spec = CommunicationSpecifications.deletedAtIsNull();

            if (!dto.getSearch().isEmpty()) {
                spec = spec.and(CommunicationSpecifications.search(dto.getSearch()));

                return new ResponseEntity(this.repository.findAll(pageRequest).getContent(), HttpStatus.OK);
            }

            return new ResponseEntity(this.repository.findAll(pageRequest).getContent(), HttpStatus.OK);
        }catch (Exception err){
            System.out.println(err.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Добавить новое средство связи
     */

    public ResponseEntity save(CommunicationDto communicationDto, String token) {
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

            Communication communication = new Communication(
                    communicationDto.getType(),
                    communicationDto.getValue());
            communication.setCreatedAt(LocalDateTime.now());

            this.repository.save(communication);
            return new ResponseEntity("Успешно сохранено", HttpStatus.CREATED);
        } catch (Exception err) {
            System.out.println(err.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Изменить средство связи
     */

    public ResponseEntity saveEdit(Long id, CommunicationDto communicationDto, String token) {
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

            Optional<Communication> current = this.repository.findById(id);

            if (current.isEmpty()) {
                return new ResponseEntity("Такого Средства связи нет", HttpStatus.NOT_FOUND);
            }

            Communication communication = current.get();
            communication = updateCommunication(communicationDto, communication);

            this.repository.save(communication);
            return new ResponseEntity("Успешно сохранено", HttpStatus.OK);
        } catch (Exception err) {
            System.out.println(err.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Удалить средство связи
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

            Optional<Communication> current = this.repository.findById(id);

            if (current.isEmpty()){
                return new ResponseEntity("Такого Средства связи нет", HttpStatus.NOT_FOUND);
            }

            Communication communication = current.get();

            if (communication.getDeletedAt() != null){
                communication.setDeletedAt(null);
                communication.setUpdatedAt(LocalDateTime.now());
            }else {
                communication.setDeletedAt(LocalDateTime.now());
            }

            this.repository.save(communication);
            return new ResponseEntity("Успешно удалено/востановлено", HttpStatus.OK);
        }catch (Exception err){
            System.out.println(err.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на сервере");
        }
    }

    /**
     * Получить средство связи
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

    private Communication updateCommunication(CommunicationDto dto, Communication communication){
        communication.setUpdatedAt(LocalDateTime.now());
        communication.setValue(dto.getValue());
        communication.setType(dto.getType());
        return communication;
    }
}
