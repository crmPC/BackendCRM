package com.backend.crm.routes.services;

import com.backend.crm.app.config.Mapper;
import com.backend.crm.app.models.response.types.Response;
import com.backend.crm.app.models.response.types.ResponseData;
import com.backend.crm.routes.DTOs.CompanyDto;
import com.backend.crm.routes.DTOs.SortDto;
import com.backend.crm.routes.models.Company;
import com.backend.crm.routes.repositories.CompanyRepository;
import com.backend.crm.routes.repositories.CompanySpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * ## Сервис компании
 *
 * @author Горелов Дмитрий
 */

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository repository;

    private final Mapper mapper;

    /**
     * Получить все компании
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

            Specification<Company> spec = CompanySpecifications.deletedAtIsNull();

            if (!dto.getSearch().isEmpty()) {
                spec = spec.and(CompanySpecifications.search(dto.getSearch()));

                return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll(spec, pageRequest).getContent());
            }

            return new ResponseData<>(HttpStatus.OK.value(), "Успешно получено", this.repository.findAll(pageRequest).getContent());
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Добавить новую компанию
     */

    public Response save(CompanyDto dto){
        try {
            System.out.println(dto);
            Company company = mapper.getMapper().map(dto, Company.class);
            company.setCreatedAt(LocalDateTime.now());
            System.out.println(company);

            this.repository.save(company);
            return new Response(HttpStatus.CREATED.value(), "Успешно сохранено");
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Изменить компанию
     */

    public Response saveEdit(Long id, CompanyDto dto) {
        try {
            Optional<Company> current = this.repository.findById(id);

            if (current.isEmpty()) {
                return new Response(HttpStatus.NOT_FOUND.value(), "Такого средства связи не существует");
            }

            Company company = editCompany(current.get(), dto);
            company.setUpdatedAt(LocalDateTime.now());

            this.repository.save(company);
            return new Response(HttpStatus.OK.value(), "Успешно сохранено");
        } catch (Exception err) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Удалить компанию
     */

    public Response deleteById(Long id){
        try {
            Optional<Company> current = this.repository.findById(id);

            if (current.isEmpty()){
                return new Response(HttpStatus.NOT_FOUND.value(), "Такого средства связи нет");
            }

            Company company = current.get();

            if (company.getDeletedAt() != null){
                company.setDeletedAt(null);
                company.setUpdatedAt(LocalDateTime.now());
            }else {
                company.setDeletedAt(LocalDateTime.now());
            }

            this.repository.save(company);
            return new Response(HttpStatus.OK.value(), "Успешно удалено/востановлено");
        }catch (Exception err){
            System.out.println(err.getMessage());
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
        }
    }

    /**
     * Получить компанию
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

    private Company editCompany(Company company, CompanyDto dto){
        company.setAddress(dto.getAddress());
        company.setContact(dto.getContact());
        company.setContactuser(dto.getContactuser());
        company.setDescription(dto.getDescription());
        company.setFormat(dto.getFormat());
        company.setInn(dto.getInn());
        company.setDomainAd(dto.getDomainAd());
        company.setEmail_domain(dto.getEmail_domain());
        company.setOfficeequip(dto.getOfficeequip());
        company.setPrefix(dto.getPrefix());

        return company;
    }
}
