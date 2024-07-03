package com.liro.medicines.dto.mappers;

import com.liro.medicines.dto.BrandDTO;
import com.liro.medicines.dto.responses.BrandResponse;
import com.liro.medicines.model.dbentities.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    BrandResponse brandToBrandResponse(Brand brand);

    @Mapping(target = "medicines", ignore = true)
    Brand brandDtoToBrand(BrandDTO brandDTO);
}
