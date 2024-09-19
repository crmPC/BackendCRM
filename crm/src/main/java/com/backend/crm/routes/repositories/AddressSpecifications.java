package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.Address;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecifications {

    public static Specification<Address> deletedAtIsNull() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNull(root.get("deletedAt"));
    }

    public static Specification<Address> search(String search) {
        return (root, query, criteriaBuilder) -> {
            Specification<Address> spec = Specification.where(deletedAtIsNull());

            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search.toLowerCase() + "%";

                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.or(
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("zipCode")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("country")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("region")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("city")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("street")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("house")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("apartment")), searchPattern)
                        )
                );
            }

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}