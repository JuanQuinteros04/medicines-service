package com.liro.medicines.repositories;

import com.liro.medicines.model.dbentities.Component;
import com.liro.medicines.model.dbentities.Disease;
import com.liro.medicines.model.dbentities.MedicineGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseRepository  extends JpaRepository<Disease, Long> {

    Page<Disease> findAllByNameContaining(String nameContaining, Pageable pageable);

}
