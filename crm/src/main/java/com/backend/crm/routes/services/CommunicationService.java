package com.backend.crm.routes.services;

import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.CommunicationDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.models.Communication;
import com.backend.crm.routes.repositories.CommunicationRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * ## Сервис комуникации
 *
 * @author Горелов Дмитрий
 */

@Service
@RequiredArgsConstructor
public class CommunicationService {
    private final CommunicationRepository repository;

    /**
     * Получить все средства связи
     */

    public Response findAllCommunicationBySort(SortDto dto) {
        try {
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
     * Добавить новое средство связи
     */

    public Response saveCommunication(CommunicationDto communicationDto) {
        try {
            Communication communication = new Communication(communicationDto.getType(),
                    communicationDto.getValue());
            communication.setCreatedAt(LocalDateTime.now());

            this.repository.save(communication);
            return new Response(HttpStatus.OK.value(), "Успешно сохранено");
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Изменить средство связи
     */

    public Response editCommunication(Long id, CommunicationDto communicationDto) {
        try {
            Optional<Communication> current = this.repository.findById(id);

            if (current.isEmpty()) {
                return new Response(HttpStatus.NOT_FOUND.value(), "Такого средства связи не существует");
            }

            Communication communication = current.get();
            communication = updateCommunication(communicationDto, communication);

            this.repository.save(communication);
            return new Response(HttpStatus.OK.value(), "Успешно сохранено");
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    private Communication updateCommunication(CommunicationDto dto, Communication communication){
        communication.setUpdatedAt(LocalDateTime.now());
        communication.setValue(dto.getValue());
        communication.setType(dto.getType());
        return communication;
    }
}
