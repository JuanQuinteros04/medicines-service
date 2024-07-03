package com.liro.medicines.dto.mappers;

import com.liro.medicines.dto.PresentationDTO;
import com.liro.medicines.dto.responses.PresentationResponse;
import com.liro.medicines.model.dbentities.Presentation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PresentationMapper {

    PresentationResponse presentationToPresentationResponse(Presentation presentation);

    Presentation presentationDtoToPresentation(PresentationDTO presentationDTO);
}
