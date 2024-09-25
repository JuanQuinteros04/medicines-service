package com.liro.medicines.service;

import com.liro.medicines.dto.MedicineDTO;
import com.liro.medicines.dto.MedicineDTOMigrator;
import com.liro.medicines.dto.responses.MedicineResponse;
import com.liro.medicines.model.dbentities.MedicineType;
import com.liro.medicines.model.enums.AnimalType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MedicineService {

    Page<MedicineResponse> findAll(Pageable pageable);

    MedicineResponse findById(Long medicineId);

    Page<MedicineResponse> findAllByCommercialNameContainingAndMedicineTypeId(String nameContaining, AnimalType animalType, Long medicineTypeId, Pageable pageable);

    MedicineResponse createMedicine(MedicineDTO medicineDTO);

    void migrateMedicines(List<MedicineDTOMigrator> medicineDTOMigratorList);

}

