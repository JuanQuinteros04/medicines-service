package com.liro.medicines.dto.mappers;

import com.liro.medicines.dto.MedicineTypeDTO;
import com.liro.medicines.dto.responses.MedicineTypeResponse;
import com.liro.medicines.model.dbentities.MedicineType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicineTypeMapper {

    MedicineTypeResponse medicineTypeToMedicineTypeResponse(MedicineType medicineType);

    MedicineType medicineTypeDTOToMedicineType(MedicineTypeDTO medicineTypeDTO);
}
