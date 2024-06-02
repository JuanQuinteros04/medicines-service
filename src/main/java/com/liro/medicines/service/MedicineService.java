package com.liro.medicines.service;

import com.liro.medicines.dto.MedicineDTO;
import com.liro.medicines.dto.responses.MedicineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicineService {

    Page<MedicineResponse> findAll(Pageable pageable);

    MedicineResponse findById(Long medicineId);

    Page<MedicineResponse> findAllByNameContaining(String nameContaining, Pageable pageable);

    Page<MedicineResponse> findAllByNameContainingAndPresentationName(String nameContaining, String presentationName, Pageable pageable);

    MedicineResponse createMedicine(MedicineDTO medicineDTO);

}
