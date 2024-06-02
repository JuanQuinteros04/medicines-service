package com.liro.medicines.dto.mappers;

import com.liro.medicines.dto.MedicineSubTypeDTO;
import com.liro.medicines.dto.responses.MedicineSubTypeResponse;
import com.liro.medicines.model.dbentities.MedicineSubType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicineSubTypeMapper {

    MedicineSubTypeResponse medicineSubTypeToMedicineSubTypeResponse(MedicineSubType medicineSubType);

    @Mapping(target = "formulas", ignore = true)
//    @Mapping(target = "parts", ignore = true)
    MedicineSubType medicineSubTypeDtoToMedicineSubType(MedicineSubTypeDTO medicineSubTypeDTO);
}
