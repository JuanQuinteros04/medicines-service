package com.liro.medicines.service.impl;

import com.liro.medicines.dto.MedicineDTO;
import com.liro.medicines.dto.UserDTO;
import com.liro.medicines.dto.mappers.MedicineMapper;
import com.liro.medicines.dto.responses.MedicineResponse;
import com.liro.medicines.exceptions.ResourceNotFoundException;
import com.liro.medicines.exceptions.UnauthorizedException;
import com.liro.medicines.model.dbentities.Brand;
import com.liro.medicines.model.dbentities.Formula;
import com.liro.medicines.model.dbentities.Medicine;
import com.liro.medicines.repositories.*;
import com.liro.medicines.service.MedicineService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;


@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;
    private final MedicineMapper medicineMapper;
    private final PresentationRepository presentationRepository;
    private final MedicineSubTypeRepository medicineSubTypeRepository;
    private final BrandRepository brandRepository;
    private final FormulaRepository formulaRepository;

    public MedicineServiceImpl(MedicineRepository medicineRepository,
                               MedicineMapper medicineMapper,
                               PresentationRepository presentationRepository,
                               MedicineSubTypeRepository medicineSubTypeRepository,
                               BrandRepository brandRepository,
                               FormulaRepository formulaRepository) {
        this.medicineRepository = medicineRepository;
        this.medicineMapper = medicineMapper;
        this.presentationRepository = presentationRepository;
        this.medicineSubTypeRepository = medicineSubTypeRepository;
        this.brandRepository = brandRepository;
        this.formulaRepository = formulaRepository;
    }

    @Override
    public Page<MedicineResponse> findAll(Pageable pageable) {
        return medicineRepository.findAll(pageable).map(medicineMapper::medicineToMedicineResponse);
    }

    @Override
    public MedicineResponse findById(Long medicineId) {
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id: " + medicineId));

        return medicineMapper.medicineToMedicineResponse(medicine);    }

    @Override
    public Page<MedicineResponse> findAllByNameContaining(String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();

        return medicineRepository.findAllByNameContaining(nameContaining, pageable)
                .map(medicineMapper::medicineToMedicineResponse);    }

    @Override
    public Page<MedicineResponse> findAllByNameContainingAndPresentationName(String nameContaining, String presentationName, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();
        presentationName = presentationName.toLowerCase();

        return medicineRepository.findAllByNameContainingAndPresentationName(nameContaining, presentationName,  pageable)
                .map(medicineMapper::medicineToMedicineResponse);    }

    @Override
    public MedicineResponse createMedicine(MedicineDTO medicineDTO) {
        if (medicineDTO.getName() != null) {
            medicineDTO.setName(medicineDTO.getName().toLowerCase());
        }
        if (medicineDTO.getFormalName() != null) {
            medicineDTO.setFormalName(medicineDTO.getFormalName().toLowerCase());
        }

        Formula baseFormula = formulaRepository.findById(medicineDTO.getBaseFormulaId())
                .orElseThrow(() -> new ResourceNotFoundException("Formula not found with id: " + medicineDTO.getBaseFormulaId()));

        Brand brand = brandRepository.findById(medicineDTO.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + medicineDTO.getBrandId()));

        Medicine medicine = medicineMapper.medicineDtoToMedicine(medicineDTO);

        if (baseFormula.getBaseFormulaOf() == null) baseFormula.setBaseFormulaOf(new HashSet<>());
        baseFormula.getBaseFormulaOf().add(medicine);
        medicine.setBaseFormula(baseFormula);

        if (brand.getMedicines() == null) brand.setMedicines(new HashSet<>());
        brand.getMedicines().add(medicine);
        medicine.setBrand(brand);

        return medicineMapper.medicineToMedicineResponse(
                medicineRepository.save(medicine)
        );    }
}
