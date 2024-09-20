package com.backend.crm.routes.repositories;

import com.backend.crm.routes.models.OfficeEquip;
import org.springframework.data.jpa.domain.Specification;

public class OfficeEquipSpecifications {

    public static Specification<OfficeEquip> deletedAtIsNull() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNull(root.get("deletedAt"));
    }

    public static Specification<OfficeEquip> search(String search) {
        return (root, query, criteriaBuilder) -> {
            Specification<OfficeEquip> spec = Specification.where(deletedAtIsNull());

            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search.toLowerCase() + "%";

                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.or(
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("name")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("description")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("serial")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("inventoryNumber")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("prim")), searchPattern),
                                criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("model")), searchPattern)
                        )
                );
            }

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
