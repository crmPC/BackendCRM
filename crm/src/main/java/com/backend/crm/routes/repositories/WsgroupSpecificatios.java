package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.WSGroup;
import org.springframework.data.jpa.domain.Specification;

public class WsgroupSpecificatios {

    public static Specification<WSGroup> deletedAtIsNull() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNull(root.get("deletedAt"));
    }

    public static Specification<WSGroup> search(String search) {
        return (root, query, criteriaBuilder) -> {
            Specification<WSGroup> spec = Specification.where(deletedAtIsNull());

            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search.toLowerCase() + "%";

                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.or(
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("objectGUID")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("dn")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("cn")), searchPattern)
                        )
                );
            }

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
