package com.backend.crm.routes.services;

import com.backend.crm.app.config.Mapper;
import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.app.utils.PasswordUtils;
import com.backend.crm.routes.DTOs.AccessUsersCompanyDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.DTOs.WSUSerDto;
import com.backend.crm.routes.models.AccessUsersCompany;
import com.backend.crm.routes.models.WSUSer;
import com.backend.crm.routes.repositories.AccessUsersCompanyRepository;
import com.backend.crm.routes.repositories.WSUSerSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccessUsersCompanyService {

    private final AccessUsersCompanyRepository repository;

    private final Mapper mapper;

    /**
     * Получить всех адиминов компании
     */

    public Response findAllBySort(SortDto dto, String token) {
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

            Specification<WSUSer> spec = WSUSerSpecifications.deletedAtIsNull();

            if (!dto.getSearch().isEmpty()) {
                spec = spec.and(WSUSerSpecifications.search(dto.getSearch()));

                return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll(pageRequest).getContent());
            }

            return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll(pageRequest).getContent());
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Добавить нового админа компании
     */

    public Response save(AccessUsersCompanyDto dto, String token) {
        try {
            AccessUsersCompany accessUsersCompany = mapper.getMapper().map(dto, AccessUsersCompany.class);

            this.repository.save(accessUsersCompany);
            return new Response(HttpStatus.CREATED.value(), "Успешно сохранено");
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Изменить админа компании
     */

    public Response saveEdit(Long id, AccessUsersCompanyDto dto, String token) {
        try {
            Optional<AccessUsersCompany> current = this.repository.findById(id);

            if (current.isEmpty()) {
                return new Response(HttpStatus.NOT_FOUND.value(), "Такого пользователя нет");
            }

            AccessUsersCompany accessUsersCompany = current.get();
            accessUsersCompany = mapper.getMapper().map(dto, AccessUsersCompany.class);


            this.repository.save(accessUsersCompany);
            return new Response(HttpStatus.OK.value(), "Успешно сохранено");
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Удалить админа компании
     */

    public Response deleteById(Long id, String token){
        try {
            this.repository.deleteById(id);
            return new Response(HttpStatus.OK.value(), "Успешно удалено/востановлено");
        }catch (Exception err){
            System.out.println(err.getMessage());
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Получить админа компании
     */

    public Response findById(Long id, String token){
        try {
            return new ResponseData(HttpStatus.OK.value(),
                    "Успешно получено",
                    this.repository.findById(id).get());
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }
}
