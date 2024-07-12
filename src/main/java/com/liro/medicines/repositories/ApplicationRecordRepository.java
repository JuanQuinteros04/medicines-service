package com.liro.medicines.repositories;
import com.liro.medicines.model.dbentities.ApplicationRecord;
import com.liro.medicines.model.dbentities.Brand;
import com.liro.medicines.model.dbentities.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRecordRepository extends JpaRepository<ApplicationRecord, Long> {


    Optional<ApplicationRecord> findTopByAnimalIdAndMedicineMedicineGroupIdOrderByApplicationDateDesc(Long animalId, Long medicineGroupId);

    Optional<ApplicationRecord> findTopByAnimalIdAndMedicineMedicineTypeIdOrderByApplicationDateDesc(Long animalId, Long medicineTypeId);

    Page<ApplicationRecord> findAllByAnimalIdAndMedicineMedicineGroupId(Long animalId, Long medicineGroupId, Pageable pageable);

    Page<ApplicationRecord> findAllByAnimalIdAndMedicineMedicineTypeId(Long animalId, Long medicineTypeId, Pageable pageable);

}