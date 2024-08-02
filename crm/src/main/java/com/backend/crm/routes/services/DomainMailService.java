package com.backend.crm.routes.services;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.DomainMailDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.models.DomainMail;
import com.backend.crm.routes.repositories.DomainMailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * ## Сервис почты домена
 *
 * @author Горелов Дмитрий
 */

@Service
@RequiredArgsConstructor
public class DomainMailService {
    private final DomainMailRepository repository;

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

    public Response save(DomainMailDto dto){
        try {
            DomainMail domainMail = new DomainMail();
            domainMail.setCompany(dto.getCompany());
            domainMail.setName(dto.getName());
            domainMail.setCreatedAt(LocalDateTime.now());

            this.repository.save(domainMail);
            return new Response(HttpStatus.CREATED.value(), "Успешно сохранено");
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }
}
