package com.liro.medicines.repositories;

import com.liro.medicines.model.dbentities.Medicine;
import com.liro.medicines.model.enums.AnimalType;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class MedicineSpecifications {

    public static Specification<Medicine> getMedicines(String nameContaining, AnimalType animalType, Long medicineTypeId) {
        return (Root<Medicine> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction(); // Start with an always-true predicate

            // Filtro por nombre
            if (nameContaining != null && !nameContaining.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("commercialName"), "%" + nameContaining + "%"));
            }

            // Si animalType no es nulo
            if (animalType != null) {
                if (animalType == AnimalType.DOG || animalType == AnimalType.CAT) {
                    Predicate animalTypePredicate = criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("animalType"), animalType),
                            criteriaBuilder.equal(root.get("animalType"), AnimalType.ALL_TYPE)
                    );
                    predicate = criteriaBuilder.and(predicate, animalTypePredicate);
                } else {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("animalType"), animalType));
                }
            }

            // Filtro por medicineTypeId
            if (medicineTypeId != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("medicineTypeId"), medicineTypeId));
            }

            return predicate;
        };
    }
}

