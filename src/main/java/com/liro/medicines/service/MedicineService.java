package com.liro.medicines.service;

import com.liro.medicines.dto.MedicineDTO;
import com.liro.medicines.dto.responses.MedicineResponse;
import com.liro.medicines.model.enums.AnimalType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicineService {

    Page<MedicineResponse> findAll(Pageable pageable);

    MedicineResponse findById(Long medicineId);

    Page<MedicineResponse> findAllByCommercialNameContaining(String nameContaining, AnimalType animalType, Pageable pageable);

    MedicineResponse createMedicine(MedicineDTO medicineDTO);

}

