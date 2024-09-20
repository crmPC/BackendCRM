package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.Communication;
import org.springframework.data.jpa.domain.Specification;

public class CommunicationSpecifications {

    public static Specification<Communication> deletedAtIsNull() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNull(root.get("deletedAt"));
    }

    public static Specification<Communication> search(String search) {
        return (root, query, criteriaBuilder) -> {
            Specification<Communication> spec = Specification.where(deletedAtIsNull());

            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search.toLowerCase() + "%";

                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.or(
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("value")), searchPattern)
                        )
                );
            }
            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
