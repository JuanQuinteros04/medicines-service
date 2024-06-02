package com.liro.medicines.service;

import com.liro.medicines.dto.MedicineSubTypeDTO;
import com.liro.medicines.dto.responses.MedicineSubTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicineSubTypeService {

    Page<MedicineSubTypeResponse> findAll(Pageable pageable);

    MedicineSubTypeResponse findById(Long medicineSubTypeId);

    Page<MedicineSubTypeResponse> findAllByNameContaining(String nameContaining, Pageable pageable);

    MedicineSubTypeResponse createMedicineSubType(MedicineSubTypeDTO medicineSubTypeDTO);

}
