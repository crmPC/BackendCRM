package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.OfficeEquipTypes;
import org.springframework.data.jpa.domain.Specification;

public class OfficeEquipTypesSpecifications {

    public static Specification<OfficeEquipTypes> deletedAtIsNull() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNull(root.get("deletedAt"));
    }

    public static Specification<OfficeEquipTypes> search(String search) {
        return (root, query, criteriaBuilder) -> {
            Specification<OfficeEquipTypes> spec = Specification.where(deletedAtIsNull());

            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search.toLowerCase() + "%";

                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.or(
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("name")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("description")), searchPattern)
                        )
                );
            }

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}