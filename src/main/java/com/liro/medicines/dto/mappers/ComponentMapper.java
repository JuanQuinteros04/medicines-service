package com.liro.medicines.dto.mappers;

import com.liro.medicines.dto.ComponentDTO;
import com.liro.medicines.dto.responses.ComponentResponse;
import com.liro.medicines.model.dbentities.Component;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComponentMapper {

    @Mapping(target = "diseaseId", source = "disease.id")
    ComponentResponse componentTocomponentResponse(Component component);

    Component componentDtoToComponent(ComponentDTO componentDTO);
}
