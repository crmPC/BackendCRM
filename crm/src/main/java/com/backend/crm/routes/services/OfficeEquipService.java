package com.backend.crm.routes.services;

import com.backend.crm.app.config.Mapper;
import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.OfficeEquipDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.models.OfficeEquip;
import com.backend.crm.routes.repositories.OfficeEquipRepository;
import com.backend.crm.routes.repositories.OfficeEquipSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
public class OfficeEquipService {
    private final OfficeEquipRepository repository;

    private final Mapper mapper;

    /**
     * Получить всю оргТехнику
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

            Specification<OfficeEquip> spec = OfficeEquipSpecifications.deletedAtIsNull();

            if (!dto.getSearch().isEmpty()) {
                spec = spec.and(OfficeEquipSpecifications.search(dto.getSearch()));

                return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll(spec, pageRequest).getContent());
            }

            return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll(pageRequest).getContent());
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Добавить оргТехнику
     */

    public Response save(OfficeEquipDto dto) {
        try {
            OfficeEquip officeEquip = mapper.getMapper().map(dto, OfficeEquip.class);
            officeEquip.setCreatedAt(LocalDateTime.now());
            
            this.repository.save(officeEquip);
            return new Response(HttpStatus.CREATED.value(), "Успешно сохранено");
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Изменить оргТехнику
     */

    public Response saveEdit(Long id, OfficeEquipDto dto) {
        try {
            Optional<OfficeEquip> current = this.repository.findById(id);

            if (current.isEmpty()) {
                return new Response(HttpStatus.NOT_FOUND.value(), "Такой группы пользователей нет");
            }

            OfficeEquip officeEquip = current.get();
            mapper.getMapper().map(dto, officeEquip);
            officeEquip.setUpdatedAt(LocalDateTime.now());

            this.repository.save(officeEquip);
            return new Response(HttpStatus.OK.value(), "Успешно сохранено");
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Удалить оргТехнику
     */

    public Response deleteById(Long id){
        try {
            Optional<OfficeEquip> current = this.repository.findById(id);

            if (current.isEmpty()){
                return new Response(HttpStatus.NOT_FOUND.value(), "Такой группы пользователей нет");
            }

            OfficeEquip officeEquip = current.get();

            if (officeEquip.getDeletedAt() != null){
                officeEquip.setDeletedAt(null);
                officeEquip.setUpdatedAt(LocalDateTime.now());
            }else {
                officeEquip.setDeletedAt(LocalDateTime.now());
            }

            this.repository.save(officeEquip);
            return new Response(HttpStatus.OK.value(), "Успешно удалено/востановлено");
        }catch (Exception err){
            System.out.println(err.getMessage());
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Получить оргТехнику
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
