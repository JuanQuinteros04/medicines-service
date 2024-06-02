package com.liro.medicines.service;
import com.liro.medicines.dto.FormulaDTO;
import com.liro.medicines.dto.responses.FormulaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface FormulaService {

    Page<FormulaResponse> findAll(Pageable pageable);

    FormulaResponse findById(Long formulaId);

    Page<FormulaResponse> findAllByNameContaining(String nameContaining, Pageable pageable);

    FormulaResponse createFormula(FormulaDTO formulaDTO);

    void addSubType(Long medicineSubTypeId, Long formulaId);

    void removeSubType(Long medicineSubTypeId, Long formulaId);

}
