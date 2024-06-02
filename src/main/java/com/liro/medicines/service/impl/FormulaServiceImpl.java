package com.liro.medicines.service.impl;

import com.liro.medicines.dto.FormulaDTO;
import com.liro.medicines.dto.responses.FormulaResponse;
import com.liro.medicines.exceptions.ResourceNotFoundException;
import com.liro.medicines.model.dbentities.Formula;
import com.liro.medicines.model.dbentities.MedicineSubType;
import com.liro.medicines.repositories.FormulaRepository;
import com.liro.medicines.repositories.MedicineSubTypeRepository;
import com.liro.medicines.service.FormulaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.liro.medicines.dto.mappers.FormulaMapper;

import java.util.HashSet;


@Service
public class FormulaServiceImpl implements FormulaService {

    private final FormulaRepository formulaRepository;
    private final FormulaMapper formulaMapper;
    private final MedicineSubTypeRepository medicineSubTypeRepository;

    public FormulaServiceImpl(FormulaRepository formulaRepository,
                              FormulaMapper formulaMapper,
                              MedicineSubTypeRepository medicineSubTypeRepository) {
        this.formulaRepository = formulaRepository;
        this.formulaMapper = formulaMapper;
        this.medicineSubTypeRepository = medicineSubTypeRepository;
    }

    @Override
    public Page<FormulaResponse> findAll(Pageable pageable) {
        return formulaRepository.findAll(pageable).map(formulaMapper::formulaToFormulaResponse);
    }

    @Override
    public FormulaResponse findById(Long formulaId) {
        Formula formula = formulaRepository.findById(formulaId)
                .orElseThrow(() -> new ResourceNotFoundException("Formula not found with id: " + formulaId));

        return formulaMapper.formulaToFormulaResponse(formula);    }

    @Override
    public Page<FormulaResponse> findAllByNameContaining(String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();

        return formulaRepository.findAllByNameContaining(nameContaining, pageable)
                .map(formulaMapper::formulaToFormulaResponse);    }

    @Override
    public FormulaResponse createFormula(FormulaDTO formulaDTO) {
        if (formulaDTO.getName() != null) {
            formulaDTO.setName(formulaDTO.getName().toLowerCase());
        }
        if (formulaDTO.getFormalName() != null) {
            formulaDTO.setFormalName(formulaDTO.getFormalName().toLowerCase());
        }

        Formula formula = formulaMapper.formulaDtoToFormula(formulaDTO);

        return formulaMapper.formulaToFormulaResponse(
                formulaRepository.save(formula)
        );    }

    @Override
    public void addSubType(Long medicineSubTypeId, Long formulaId) {

        Formula formula = formulaRepository.findById(formulaId)
                .orElseThrow(() -> new ResourceNotFoundException("Formula not found with id: " + formulaId));
        if (formula.getMedicineSubTypes() == null) formula.setMedicineSubTypes(new HashSet<>());

        MedicineSubType medicineSubType = medicineSubTypeRepository.findById(medicineSubTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("MedicineSubType not found with id: " + medicineSubTypeId));
        if (medicineSubType.getFormulas() == null) medicineSubType.setFormulas(new HashSet<>());

        formula.getMedicineSubTypes().add(medicineSubType);
        medicineSubType.getFormulas().add(formula);

        medicineSubTypeRepository.save(medicineSubType);
    }

    @Override
    public void removeSubType(Long medicineSubTypeId, Long formulaId) {

        Formula formula = formulaRepository.findById(formulaId)
                .orElseThrow(() -> new ResourceNotFoundException("Formula not found with id: " + formulaId));

        MedicineSubType medicineSubType = medicineSubTypeRepository
                .findByIdAndFormulasId(medicineSubTypeId, formulaId)
                .orElseThrow(() -> new ResourceNotFoundException("MedicineSubType not found with id: "
                        + medicineSubTypeId + " and formulaId: " + formulaId));

        formula.getMedicineSubTypes().remove(medicineSubType);
        medicineSubType.getFormulas().remove(formula);

        formulaRepository.save(formula);
        medicineSubTypeRepository.save(medicineSubType);
    }
}
