package com.liro.medicines.dto.mappers;

import com.liro.medicines.dto.FormulaDTO;
import com.liro.medicines.dto.responses.FormulaResponse;
import com.liro.medicines.model.dbentities.Formula;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FormulaMapper {

    FormulaResponse formulaToFormulaResponse(Formula formula);

    @Mapping(target = "medicineSubTypes", ignore = true)
    @Mapping(target = "baseFormulaOf", ignore = true)
    Formula formulaDtoToFormula(FormulaDTO formulaDTO);

}
