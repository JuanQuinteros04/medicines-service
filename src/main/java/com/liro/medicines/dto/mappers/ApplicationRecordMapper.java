package com.liro.medicines.dto.mappers;


import com.liro.medicines.dto.ApplicationRecordDTO;
import com.liro.medicines.dto.BrandDTO;
import com.liro.medicines.dto.migrator.ApplicationRecordDTOMigrator;
import com.liro.medicines.dto.responses.ApplicationRecordResponse;
import com.liro.medicines.dto.responses.BrandResponse;
import com.liro.medicines.model.dbentities.ApplicationRecord;
import com.liro.medicines.model.dbentities.Brand;
import com.netflix.discovery.shared.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ApplicationRecordMapper {

    ApplicationRecordResponse applicationRecordToApplicationRecordResponse(ApplicationRecord applicationRecord);

    @Mapping(target = "medicine", ignore = true)
    ApplicationRecord applicationRecordDtoToApplicationRecord(ApplicationRecordDTO applicationRecordDTO);

    @Mapping(target = "vetProfileId", ignore = true)
    @Mapping(target = "vetClinicId", ignore = true)
    @Mapping(target = "applicationDate", ignore = true)
    @Mapping(target = "animalId", ignore = true)
    @Mapping(target = "medicine", ignore = true)
    ApplicationRecord updateApplicationRecord(ApplicationRecordDTO applicationRecordDTO, @MappingTarget ApplicationRecord applicationRecord);

    @Mapping(target = "details", ignore = true)
    @Mapping(target = "quantity", ignore = true)
    @Mapping(target = "lote", ignore = true)
    @Mapping(target = "controlNumber", ignore = true)
    @Mapping(target = "serieNumber", ignore = true)
    @Mapping(target = "medicine", ignore = true)
    ApplicationRecord applicationRecordDTOMigratorToApplicationRecord(ApplicationRecordDTOMigrator applicationRecordDTOMigrator);
}
