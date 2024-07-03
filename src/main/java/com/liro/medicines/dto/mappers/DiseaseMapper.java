package com.liro.medicines.dto.mappers;

import com.liro.medicines.dto.DiseaseDTO;
import com.liro.medicines.dto.responses.DiseaseResponse;
import com.liro.medicines.model.dbentities.Disease;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiseaseMapper {

    DiseaseResponse diseaseToDiseaseResponse(Disease disease);

    Disease diseaseDtoToDisease(DiseaseDTO diseaseDTO);
}
