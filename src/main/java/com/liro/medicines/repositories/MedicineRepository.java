package com.liro.medicines.repositories;

import com.liro.medicines.model.dbentities.Medicine;
import com.liro.medicines.model.dbentities.MedicineType;
import com.liro.medicines.model.enums.AnimalType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {


        Page<Medicine> findAllByCommercialNameContaining(@Nullable Specification<Medicine> specifications, Pageable pageable);

}
