package com.liro.medicines.service;

import com.liro.medicines.dto.MedicineGroupDTO;
import com.liro.medicines.dto.MedicineTypeDTO;
import com.liro.medicines.dto.PresentationDTO;
import com.liro.medicines.dto.responses.MedicineGroupResponse;
import com.liro.medicines.dto.responses.MedicineTypeResponse;
import com.liro.medicines.dto.responses.PresentationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicineGroupService {
    Page<MedicineGroupResponse> findAll(Pageable pageable);

    MedicineGroupResponse findById(Long medicineGroupId);

    Page<MedicineGroupResponse> findAllByNameContaining(String nameContaining, Pageable pageable);

    MedicineGroupResponse createMedicineGroup(MedicineGroupDTO medicineGroupDTO);
}
