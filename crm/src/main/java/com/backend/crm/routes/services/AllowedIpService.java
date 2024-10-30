package com.backend.crm.routes.services;

import com.backend.crm.app.config.Mapper;
import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.AllowedIpDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.models.AllowedIp;
import com.backend.crm.routes.repositories.AllowedIpRepository;
import com.backend.crm.routes.repositories.AllowedIpSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    /**
     * Получить вcе IP LDAP
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

            Specification<AllowedIp> spec = AllowedIpSpecifications.deletedAtIsNull();

            if (!dto.getSearch().isEmpty()) {
                spec = spec.and(AllowedIpSpecifications.search(dto.getSearch()));

                return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll(spec, pageRequest).getContent());
            }

            return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll(pageRequest).getContent());
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Добавить IP LDAP
     */

    public Response save(AllowedIpDto dto) {
        try {
            AllowedIp allowedIp = mapper.getMapper().map(dto, AllowedIp.class);
            allowedIp.setCreatedAt(LocalDateTime.now());

            System.out.println(allowedIp);
            this.repository.save(allowedIp);
            return new Response(HttpStatus.CREATED.value(), "Успешно сохранено");
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Изменить IP LDAP
     */

    public Response saveEdit(Long id, AllowedIpDto dto) {
        try {
            Optional<AllowedIp> current = this.repository.findById(id);

            if (current.isEmpty()) {
                return new Response(HttpStatus.NOT_FOUND.value(), "Такой группы пользователей нет");
            }

            AllowedIp allowedIp = current.get();
            mapper.getMapper().map(dto, allowedIp);
            allowedIp.setUpdatedAt(LocalDateTime.now());

            this.repository.save(allowedIp);
            return new Response(HttpStatus.OK.value(), "Успешно сохранено");
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Удалить IP LDAP
     */

    public Response deleteById(Long id){
        try {
            Optional<AllowedIp> current = this.repository.findById(id);

            if (current.isEmpty()){
                return new Response(HttpStatus.NOT_FOUND.value(), "Такой группы пользователей нет");
            }

            AllowedIp allowedIp = current.get();

            if (allowedIp.getDeletedAt() != null){
                allowedIp.setDeletedAt(null);
                allowedIp.setUpdatedAt(LocalDateTime.now());
            }else {
                allowedIp.setDeletedAt(LocalDateTime.now());
            }

            this.repository.save(allowedIp);
            return new Response(HttpStatus.OK.value(), "Успешно удалено/востановлено");
        }catch (Exception err){
            System.out.println(err.getMessage());
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Получить IP LDAP
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
