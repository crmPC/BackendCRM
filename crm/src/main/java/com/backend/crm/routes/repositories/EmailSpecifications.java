package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.Email;
import org.springframework.data.jpa.domain.Specification;

public class EmailSpecifications {

    public static Specification<Email> deletedAtIsNull() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNull(root.get("deletedAt"));
    }

    public static Specification<Email> search(String search) {
        return (root, query, criteriaBuilder) -> {
            Specification<Email> spec = Specification.where(deletedAtIsNull());

            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search.toLowerCase() + "%";

                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.or(
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("name")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("name_with_domain")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("password")), searchPattern)
                        )
                );
            }

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
