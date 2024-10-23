package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.WSUSer;
import org.springframework.data.jpa.domain.Specification;

public class WSUSerSpecifications {

    public static Specification<WSUSer> deletedAtIsNull() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNull(root.get("deletedAt"));
    }

    public static Specification<WSUSer> search(String search) {
        return (root, query, criteriaBuilder) -> {
            Specification<WSUSer> spec = Specification.where(deletedAtIsNull());

            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search.toLowerCase() + "%";

                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.or(
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("fullname")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("dob")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("prim")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("password")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("objectClass")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("member")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("cn")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("sn")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("displayName")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("name")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("userPrincipalName")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("login")), searchPattern)
                        )
                );
            }

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
