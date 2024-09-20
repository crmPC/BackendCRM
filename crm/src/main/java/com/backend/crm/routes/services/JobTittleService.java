package com.backend.crm.routes.services;

import com.backend.crm.app.config.Mapper;
import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.JobTittleDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.models.JobTittle;
import com.backend.crm.routes.repositories.JobTittleRepository;
import com.backend.crm.routes.repositories.JobTittleSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * ## Сервис должностей
 *
 * @author Горелов Дмитрий
 */

@Service
@RequiredArgsConstructor
public class JobTittleService {
    private final JobTittleRepository repository;

    private final Mapper mapper;

    /**
     * Получить все должности
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

            Specification<JobTittle> spec = JobTittleSpecifications.deletedAtIsNull();

            if (!dto.getSearch().isEmpty()) {
                spec = spec.and(JobTittleSpecifications.search(dto.getSearch()));

                return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll(spec, pageRequest).getContent());
            }

            return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll(pageRequest).getContent());
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Добавить новую должность
     */

    public Response save(JobTittleDto dto){
        try {
            JobTittle jobTittle = mapper.getMapper().map(dto, JobTittle.class);
            jobTittle.setCreatedAt(LocalDateTime.now());

            this.repository.save(jobTittle);
            return new Response(HttpStatus.CREATED.value(), "Успешно сохранено");
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Изменить существующую должность
     */

    public Response saveEdit(Long id, JobTittleDto dto){
        try {
            Optional<JobTittle> current = this.repository.findById(id);

            if (current.isEmpty()){
                return new Response(HttpStatus.NOT_FOUND.value(), "Такой должности нет");
            }

            JobTittle jobTittle = current.get();
            jobTittle.setName(dto.getName());
            jobTittle.setDescription(dto.getDescription());
            jobTittle.setUpdatedAt(LocalDateTime.now());

            this.repository.save(jobTittle);
            return new Response(HttpStatus.OK.value(), "Успешно сохранено");
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Удалить должность
     */

    public Response deleteById(Long id){
        try {
            Optional<JobTittle> current = this.repository.findById(id);

            if (current.isEmpty()){
                return new Response(HttpStatus.NOT_FOUND.value(), "Такого средства связи нет");
            }

            JobTittle jobTittle = current.get();

            if (jobTittle.getDeletedAt() != null){
                jobTittle.setDeletedAt(null);
                jobTittle.setUpdatedAt(LocalDateTime.now());
            }else {
                jobTittle.setDeletedAt(LocalDateTime.now());
            }

            this.repository.save(jobTittle);
            return new Response(HttpStatus.OK.value(), "Успешно удалено/востановлено");
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Получить должность
     */

    public Response findById(Long id){
        try {
            return new ResponseData<>(HttpStatus.OK.value(),
                    "Успешно получено",
                    this.repository.findById(id).get());
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }
}
