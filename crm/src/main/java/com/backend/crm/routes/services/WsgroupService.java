package com.backend.crm.routes.services;

import com.backend.crm.app.config.Mapper;
import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.DTOs.WSGroupDto;
import com.backend.crm.routes.DTOs.WSUSerDto;
import com.backend.crm.routes.models.WSGroup;
import com.backend.crm.routes.repositories.WsgroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * ## Сервис групп пользователей
 *
 * @author Горелов Дмитрий
 */

@Service
@RequiredArgsConstructor
public class WsgroupService {

    private final WsgroupRepository repository;

    private final Mapper mapper;


    /**
     * Получить все группы пользователей
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
     * Добавить новую группу пользователей
     */

    public Response save(WSUSerDto dto) {
        try {
            WSGroup wsGroup = mapper.getMapper().map(dto, WSGroup.class);
            wsGroup.setCreatedAt(LocalDateTime.now());

            this.repository.save(wsGroup);
            return new Response(HttpStatus.CREATED.value(), "Успешно сохранено");
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Изменить группу пользователей
     */

    public Response saveEdit(Long id, WSGroupDto dto) {
        try {
            Optional<WSGroup> current = this.repository.findById(id);

            if (current.isEmpty()) {
                return new Response(HttpStatus.NOT_FOUND.value(), "Такой группы пользователей нет");
            }

            WSGroup wsGroup = current.get();
            wsGroup = mapper.getMapper().map(dto, WSGroup.class);
            wsGroup.setUpdatedAt(LocalDateTime.now());

            this.repository.save(wsGroup);
            return new Response(HttpStatus.OK.value(), "Успешно сохранено");
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Удалить группу пользователей
     */

    public Response deleteById(Long id){
        try {
            Optional<WSGroup> current = this.repository.findById(id);

            if (current.isEmpty()){
                return new Response(HttpStatus.NOT_FOUND.value(), "Такой группы пользователей нет");
            }

            WSGroup wsGroup = current.get();

            if (wsGroup.getDeletedAt() != null){
                wsGroup.setDeletedAt(null);
                wsGroup.setUpdatedAt(LocalDateTime.now());
            }else {
                wsGroup.setDeletedAt(LocalDateTime.now());
            }

            this.repository.save(wsGroup);
            return new Response(HttpStatus.OK.value(), "Успешно удалено/востановлено");
        }catch (Exception err){
            System.out.println(err.getMessage());
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Получить группу пользователей
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
