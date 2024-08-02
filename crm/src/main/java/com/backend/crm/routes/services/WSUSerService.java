package com.backend.crm.routes.services;

import com.backend.crm.app.config.Mapper;
import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.DTOs.WSUSerDto;
import com.backend.crm.routes.models.Pass;
import com.backend.crm.routes.models.WSUSer;
import com.backend.crm.routes.repositories.WSUSerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * ## Сервис пользователей
 *
 * @author Горелов Дмитрий
 */

@Service
@RequiredArgsConstructor
public class WSUSerService {
    private final WSUSerRepository repository;

    private final Mapper mapper;

    /**
     * Получить всех пользователей
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
     * Добавить нового пользователя
     */

    public Response save(WSUSerDto dto) {
        try {
            //приходит объект респонса

            WSUSer wsuSer = mapper.getMapper().map(dto, WSUSer.class);
            wsuSer.setCreatedAt(LocalDateTime.now());

            this.repository.save(wsuSer);
            return new Response(HttpStatus.CREATED.value(), "Успешно сохранено");
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Изменить пользователя
     */

    public Response saveEdit(Long id, WSUSerDto dto) {
        try {
            Optional<WSUSer> current = this.repository.findById(id);

            if (current.isEmpty()) {
                return new Response(HttpStatus.NOT_FOUND.value(), "Такого средства связи не существует");
            }

            WSUSer wsuSer = current.get();
            wsuSer = mapper.getMapper().map(dto, WSUSer.class);
            wsuSer.setUpdatedAt(LocalDateTime.now());

            this.repository.save(wsuSer);
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
            Optional<WSUSer> current = this.repository.findById(id);

            if (current.isEmpty()){
                return new Response(HttpStatus.NOT_FOUND.value(), "Такого средства связи нет");
            }

            WSUSer wsuSer = current.get();

            if (wsuSer.getDeletedAt() != null){
                wsuSer.setDeletedAt(null);
                wsuSer.setUpdatedAt(LocalDateTime.now());
            }else {
                wsuSer.setDeletedAt(LocalDateTime.now());
            }

            this.repository.save(wsuSer);
            return new Response(HttpStatus.OK.value(), "Успешно удалено/востановлено");
        }catch (Exception err){
            System.out.println(err.getMessage());
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Получить пользователя
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
