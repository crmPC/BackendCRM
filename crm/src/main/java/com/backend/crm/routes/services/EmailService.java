package com.backend.crm.routes.services;

import ch.qos.logback.classic.model.processor.LogbackClassicDefaultNestedComponentRules;
import com.backend.crm.app.config.Mapper;
import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.EmailDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.models.Email;
import com.backend.crm.routes.repositories.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * ## Сервис почты
 *
 * @author Горелов Дмитрий
 */


@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailRepository repository;
    private final Mapper mapper;

    /**
     * Получить все почты
     */

    public Response findAllBySort(SortDto dto) {
        try {
            if (dto.getSort().isEmpty()){
                return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll());
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

            return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll(pageRequest).getContent());
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Добавить новую почту
     */

    public Response save(EmailDto dto){
        try {
            Email email = mapper.getMapper().map(dto, Email.class);
            email.setCreatedAt(LocalDateTime.now());
            email.setDomainMail(dto.getDomainmail());

            this.repository.save(email);
            return new Response(HttpStatus.CREATED.value(), "Успешно сохранено");
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Изменить почту
     */

    public Response saveEdit(Long id, EmailDto dto) {
        try {
            Optional<Email> current = this.repository.findById(id);

            if (current.isEmpty()) {
                return new Response(HttpStatus.NOT_FOUND.value(), "Такого средства связи не существует");
            }

            Email email = current.get();
            email.setName(dto.getName());
            email.setDomainMail(dto.getDomainmail());
            email.setPassword(dto.getPassword());
            email.setUpdatedAt(LocalDateTime.now());

            this.repository.save(email);
            return new Response(HttpStatus.OK.value(), "Успешно сохранено");
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Удалить почту
     */

    public Response deleteById(Long id){
        try {
            Optional<Email> current = this.repository.findById(id);

            if (current.isEmpty()){
                return new Response(HttpStatus.NOT_FOUND.value(), "Такого средства связи нет");
            }

            Email email = current.get();

            if (email.getDeletedAt() != null){
                email.setDeletedAt(null);
                email.setUpdatedAt(LocalDateTime.now());
            }else {
                email.setDeletedAt(LocalDateTime.now());
            }

            this.repository.save(email);
            return new Response(HttpStatus.OK.value(), "Успешно удалено/востановлено");
        }catch (Exception err){
            System.out.println(err.getMessage());
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Получить почту
     */

    public Response findById(Long id){
        try {
            return new ResponseData(HttpStatus.OK.value(),
                    "Успешно получено",
                    this.repository.findById(id).get());
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }
}
