package com.liro.medicines.service;

import com.liro.medicines.dto.MedicineTypeDTO;
import com.liro.medicines.dto.responses.MedicineTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicineTypeService {

    Page<MedicineTypeResponse> findAll(Pageable pageable);

    MedicineTypeResponse findById(Long medicineTypeId);

    Page<MedicineTypeResponse> findAllByNameContaining(String nameContaining, Pageable pageable);

    MedicineTypeResponse createMedicineType(MedicineTypeDTO medicineTypeDTO);

}
