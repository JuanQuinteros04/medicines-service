package com.liro.medicines.dto.mappers;

import com.liro.medicines.dto.MedicineGroupDTO;
import com.liro.medicines.dto.responses.MedicineGroupResponse;
import com.liro.medicines.model.dbentities.MedicineGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface MedicineGroupMapper {

    MedicineGroupResponse medicineGroupToMedicineGroupResponse(MedicineGroup medicineGroup);

    MedicineGroup medicineGroupDTOToMedicineGroup(MedicineGroupDTO medicineGroupDTO);
}
