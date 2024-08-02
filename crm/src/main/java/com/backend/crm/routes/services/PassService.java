package com.backend.crm.routes.services;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.CommunicationDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.models.Communication;
import com.backend.crm.routes.models.Pass;
import com.backend.crm.routes.repositories.PassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * ## Сервис пропусков
 *
 * @author Горелов Дмитрий
 */

@Service
@RequiredArgsConstructor
public class PassService {
    private final PassRepository repository;

    /**
     * Получить все пропуска
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
     * Добавить новый пропуск
     */

    public Response save(String name) {
        try {
            Pass pass = new Pass();
            pass.setNumber(name);
            pass.setCreatedAt(LocalDateTime.now());

            this.repository.save(pass);
            return new Response(HttpStatus.CREATED.value(), "Успешно сохранено");
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Изменить пропуск
     */

    public Response saveEdit(Long id, String name) {
        try {
            Optional<Pass> current = this.repository.findById(id);

            if (current.isEmpty()) {
                return new Response(HttpStatus.NOT_FOUND.value(), "Такого средства связи не существует");
            }

            Pass pass = current.get();
            pass.setNumber(name);
            pass.setUpdatedAt(LocalDateTime.now());

            this.repository.save(pass);
            return new Response(HttpStatus.OK.value(), "Успешно сохранено");
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Удалить пропуск
     */

    public Response deleteById(Long id){
        try {
            Optional<Pass> current = this.repository.findById(id);

            if (current.isEmpty()){
                return new Response(HttpStatus.NOT_FOUND.value(), "Такого средства связи нет");
            }

            Pass pass = current.get();

            if (pass.getDeletedAt() != null){
                pass.setDeletedAt(null);
                pass.setUpdatedAt(LocalDateTime.now());
            }else {
                pass.setDeletedAt(LocalDateTime.now());
            }

            this.repository.save(pass);
            return new Response(HttpStatus.OK.value(), "Успешно удалено/востановлено");
        }catch (Exception err){
            System.out.println(err.getMessage());
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Получить пропуск
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
