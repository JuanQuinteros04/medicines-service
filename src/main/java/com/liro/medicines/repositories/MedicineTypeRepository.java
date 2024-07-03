package com.liro.medicines.repositories;

import com.liro.medicines.model.dbentities.Medicine;
import com.liro.medicines.model.dbentities.MedicineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicineTypeRepository extends JpaRepository<MedicineType, Long> {

    Page<MedicineType> findAllByNameContaining(String nameContaining, Pageable pageable);
}
