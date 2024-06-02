package com.liro.medicines.repositories;

import com.liro.medicines.model.dbentities.MedicineSubType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicineSubTypeRepository extends JpaRepository<MedicineSubType, Long> {

    Page<MedicineSubType> findAllByNameContaining(String nameContaining, Pageable pageable);

    Optional<MedicineSubType> findByIdAndFormulasId(Long medicineSubTypeId, Long formulaId);

}
