package com.liro.medicines.service;

import com.liro.medicines.dto.ApplicationRecordDTO;
import com.liro.medicines.dto.responses.ApplicationRecordResponse;
import com.liro.medicines.model.dbentities.ApplicationRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApplicationRecordService {

    Page<ApplicationRecordResponse> findAll(Pageable pageable);

    ApplicationRecordResponse findById(Long applicationRecordId);

    ApplicationRecordResponse createApplicationRecord(ApplicationRecordDTO applicationRecordDTO, String token);
    ApplicationRecordResponse getLatestApplicationsForEachMedicineGroup(Long animalId, Long medicineTypeId);
    ApplicationRecordResponse getLatestApplicationsForEachMedicineType(Long animalId, Long medicineTypeId);

    Page<ApplicationRecordResponse> findAllByAnimalIdAndMedicineMedicineGroupId(Pageable pageable, Long animalId, Long medicineGroupId);
    Page<ApplicationRecordResponse> findAllByAnimalIdAndMedicineMedicineTypeId(Pageable pageable, Long animalId, Long medicineTypeId);
}
