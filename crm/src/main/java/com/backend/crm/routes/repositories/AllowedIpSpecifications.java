package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.AllowedIp;
import org.springframework.data.jpa.domain.Specification;

public class AllowedIpSpecifications {

    public static Specification<AllowedIp> deletedAtIsNull() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNull(root.get("deletedAt"));
    }

    public static Specification<AllowedIp> search(String search) {
        return (root, query, criteriaBuilder) -> {
            Specification<AllowedIp> spec = Specification.where(deletedAtIsNull());

            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search.toLowerCase() + "%";

                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.or(
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("addres")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("prim")), searchPattern)
                        )
                );
            }

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
