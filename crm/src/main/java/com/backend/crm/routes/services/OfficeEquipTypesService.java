package com.backend.crm.routes.services;

import com.backend.crm.app.config.Mapper;
import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.OfficeEquipTypesDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.models.OfficeEquipTypes;
import com.backend.crm.routes.repositories.OfficeEquipTypesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * ## Сервис оргТехнику
 *
 * @author Горелов Дмитрий
 */

@Service
@RequiredArgsConstructor
public class OfficeEquipTypesService {

    private final OfficeEquipTypesRepository repository;

    private final Mapper mapper;

    /**
     * Получить вcе типы оргТехнику
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
     * Добавить тип оргТехники
     */

    public Response save(OfficeEquipTypesDto dto) {
        try {
            OfficeEquipTypes officeEquipTypes = mapper.getMapper().map(dto, OfficeEquipTypes.class);
            officeEquipTypes.setCreatedAt(LocalDateTime.now());

            this.repository.save(officeEquipTypes);
            return new Response(HttpStatus.CREATED.value(), "Успешно сохранено");
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Изменить тип оргТехники
     */

    public Response saveEdit(Long id, OfficeEquipTypesDto dto) {
        try {
            Optional<OfficeEquipTypes> current = this.repository.findById(id);

            if (current.isEmpty()) {
                return new Response(HttpStatus.NOT_FOUND.value(), "Такой группы пользователей нет");
            }

            OfficeEquipTypes officeEquipTypes = current.get();
            mapper.getMapper().map(dto, officeEquipTypes);
            officeEquipTypes.setUpdatedAt(LocalDateTime.now());

            this.repository.save(officeEquipTypes);
            return new Response(HttpStatus.OK.value(), "Успешно сохранено");
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Удалить тип оргТехники
     */

    public Response deleteById(Long id){
        try {
            Optional<OfficeEquipTypes> current = this.repository.findById(id);

            if (current.isEmpty()){
                return new Response(HttpStatus.NOT_FOUND.value(), "Такой группы пользователей нет");
            }

            OfficeEquipTypes officeEquipTypes = current.get();

            if (officeEquipTypes.getDeletedAt() != null){
                officeEquipTypes.setDeletedAt(null);
                officeEquipTypes.setUpdatedAt(LocalDateTime.now());
            }else {
                officeEquipTypes.setDeletedAt(LocalDateTime.now());
            }

            this.repository.save(officeEquipTypes);
            return new Response(HttpStatus.OK.value(), "Успешно удалено/востановлено");
        }catch (Exception err){
            System.out.println(err.getMessage());
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Получить тип оргТехники
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
