package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.Company;
import org.springframework.data.jpa.domain.Specification;

public class CompanySpecifications {

    public static Specification<Company> deletedAtIsNull() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNull(root.get("deletedAt"));
    }

    public static Specification<Company> search(String search) {
        return (root, query, criteriaBuilder) -> {
            Specification<Company> spec = Specification.where(deletedAtIsNull());

            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search.toLowerCase() + "%";

                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.or(
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("name")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("contact")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("INN")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("prefix")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("email_domain")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("description")), searchPattern)
                                )
                );
            }

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
