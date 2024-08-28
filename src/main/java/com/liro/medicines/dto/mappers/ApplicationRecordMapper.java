package com.liro.medicines.dto.mappers;


import com.liro.medicines.dto.ApplicationRecordDTO;
import com.liro.medicines.dto.BrandDTO;
import com.liro.medicines.dto.responses.ApplicationRecordResponse;
import com.liro.medicines.dto.responses.BrandResponse;
import com.liro.medicines.model.dbentities.ApplicationRecord;
import com.liro.medicines.model.dbentities.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationRecordMapper {

    ApplicationRecordResponse applicationRecordToApplicationRecordResponse(ApplicationRecord applicationRecord);

    @Mapping(target = "medicine", ignore = true)
    ApplicationRecord applicationRecordDtoToApplicationRecord(ApplicationRecordDTO applicationRecordDTO);
}
