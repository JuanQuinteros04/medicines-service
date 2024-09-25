package com.liro.medicines.repositories;

import com.liro.medicines.model.dbentities.MedicineGroup;
import com.liro.medicines.model.dbentities.MedicineType;
import com.liro.medicines.model.dbentities.Presentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicineGroupRepository extends JpaRepository<MedicineGroup, Long> {

    Page<MedicineGroup> findAllByNameContaining(String nameContaining, Pageable pageable);
    Optional<MedicineGroup> findByName(String name);

}
