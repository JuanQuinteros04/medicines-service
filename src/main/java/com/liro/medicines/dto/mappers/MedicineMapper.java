package com.liro.medicines.dto.mappers;

import com.liro.medicines.dto.MedicineDTO;
import com.liro.medicines.dto.responses.MedicineResponse;
import com.liro.medicines.model.dbentities.Component;
import com.liro.medicines.model.dbentities.Medicine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MedicineMapper {

    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "medicineTypeId", source = "medicineType.id")
    @Mapping(target = "medicineGroupId", source = "medicineGroup.id")
    @Mapping(target = "presentationId", source = "presentation.id")
    @Mapping(target = "components", source = "components")
    MedicineResponse medicineToMedicineResponse(Medicine medicine);

    @Mapping(target = "presentation", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "medicineType", ignore = true)
    @Mapping(target = "medicineGroup", ignore = true)
    @Mapping(target = "components", ignore = true)
    Medicine medicineDtoToMedicine(MedicineDTO medicineDTO);

    default List<Long> mapComponentsToIds(Set<Component> components) {
        return components.stream()
                .map(Component::getId)
                .collect(Collectors.toList());
    }
}
