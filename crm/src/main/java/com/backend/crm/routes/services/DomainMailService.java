package com.backend.crm.routes.services;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.DomainMailDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.models.DomainMail;
import com.backend.crm.routes.repositories.DomainMailRepository;
import com.backend.crm.routes.repositories.DomainMailSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

            Specification<DomainMail> spec = DomainMailSpecifications.deletedAtIsNull();

            if (!dto.getSearch().isEmpty()) {
                spec = spec.and(DomainMailSpecifications.search(dto.getSearch()));

                return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll(spec, pageRequest).getContent());
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

    /**
     * Изменить почту
     */

    public Response saveEdit(Long id, DomainMailDto dto) {
        try {
            Optional<DomainMail> current = this.repository.findById(id);

            if (current.isEmpty()) {
                return new Response(HttpStatus.NOT_FOUND.value(), "Такого средства связи не существует");
            }

            DomainMail domainMail = current.get();
            domainMail.setCompany(dto.getCompany());
            domainMail.setName(dto.getName());
            domainMail.setUpdatedAt(LocalDateTime.now());

            this.repository.save(domainMail);
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
            Optional<DomainMail> current = this.repository.findById(id);

            if (current.isEmpty()){
                return new Response(HttpStatus.NOT_FOUND.value(), "Такого средства связи нет");
            }

            DomainMail domainMail = current.get();

            if (domainMail.getDeletedAt() != null){
                domainMail.setDeletedAt(null);
                domainMail.setUpdatedAt(LocalDateTime.now());
            }else {
                domainMail.setDeletedAt(LocalDateTime.now());
            }

            this.repository.save(domainMail);
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
