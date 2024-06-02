package com.liro.medicines.dto.mappers;

import com.liro.medicines.dto.MedicineDTO;
import com.liro.medicines.dto.responses.MedicineResponse;
import com.liro.medicines.model.dbentities.Medicine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicineMapper {

    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "baseFormulaId", source = "baseFormula.id")
    MedicineResponse medicineToMedicineResponse(Medicine medicine);

    @Mapping(target = "presentation", ignore = true)
//    @Mapping(target = "laboratoryBranches", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "baseFormula", ignore = true)
    Medicine medicineDtoToMedicine(MedicineDTO medicineDTO);

}
