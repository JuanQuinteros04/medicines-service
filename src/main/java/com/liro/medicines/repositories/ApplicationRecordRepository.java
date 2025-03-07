package com.liro.medicines.repositories;
import com.liro.medicines.model.dbentities.ApplicationRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ApplicationRecordRepository extends JpaRepository<ApplicationRecord, Long> {


    Optional<ApplicationRecord> findTopByAnimalIdAndMedicine_MedicineGroups_IdOrderByApplicationDateDesc(Long animalId, Long medicineGroupId);

    Optional<ApplicationRecord> findTopByAnimalIdAndMedicineMedicineTypeIdOrderByApplicationDateDesc(Long animalId, Long medicineTypeId);

    Page<ApplicationRecord> findAllByAnimalIdAndMedicine_MedicineGroups_Id(Long animalId, Long medicineGroupId, Pageable pageable);

    Page<ApplicationRecord> findAllByAnimalIdAndMedicineMedicineTypeId(Long animalId, Long medicineTypeId, Pageable pageable);

    @Transactional
    void deleteByAnimalId(Long animalId);

}