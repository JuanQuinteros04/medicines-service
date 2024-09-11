package com.liro.medicines.repositories;

import com.liro.medicines.model.dbentities.Medicine;
import com.liro.medicines.model.dbentities.MedicineType;
import com.liro.medicines.model.enums.AnimalType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {


        Page<Medicine> findAllByCommercialNameContaining(String nameContaining, Pageable pageable);

        Page<Medicine> findAllByCommercialNameContainingAndAnimalTypeInAndMedicineTypeId(String nameContaining, List<AnimalType> animalTypes, Long medicineId, Pageable pageable);
        Page<Medicine> findAllByCommercialNameContainingAndAnimalTypeIn(String nameContaining, List<AnimalType> animalTypes, Pageable pageable);

}
