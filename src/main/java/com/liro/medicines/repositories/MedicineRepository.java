package com.liro.medicines.repositories;

import com.liro.medicines.model.dbentities.Medicine;
import com.liro.medicines.model.dbentities.MedicineType;
import com.liro.medicines.model.enums.AnimalType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    Page<Medicine> findAllByCommercialNameContainingAndAnimalType(String nameContaining, AnimalType animalType, Pageable pageable);

    Page<Medicine> findAllByCommercialNameContainingAndAnimalTypeAndMedicineType(String nameContaining, AnimalType animalType, MedicineType medicineType, Pageable pageable);


    Page<Medicine> findAllByCommercialNameContaining(String nameContaining, Pageable pageable);


}
