package com.liro.medicines.service.impl;

import com.liro.medicines.dto.MedicineSubTypeDTO;
import com.liro.medicines.dto.mappers.MedicineSubTypeMapper;
import com.liro.medicines.dto.responses.MedicineSubTypeResponse;
import com.liro.medicines.model.dbentities.MedicineSubType;
import com.liro.medicines.repositories.MedicineSubTypeRepository;
import com.liro.medicines.service.MedicineSubTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.liro.medicines.exceptions.ResourceNotFoundException;


public class MedicineSubTypeServiceImpl implements MedicineSubTypeService {

    private final MedicineSubTypeRepository medicineSubTypeRepository;
    private final MedicineSubTypeMapper medicineSubTypeMapper;

    public MedicineSubTypeServiceImpl(MedicineSubTypeRepository medicineSubTypeRepository, MedicineSubTypeMapper medicineSubTypeMapper) {
        this.medicineSubTypeRepository = medicineSubTypeRepository;
        this.medicineSubTypeMapper = medicineSubTypeMapper;
    }

    @Override
    public Page<MedicineSubTypeResponse> findAll(Pageable pageable) {
        return medicineSubTypeRepository.findAll(pageable).map(medicineSubTypeMapper::medicineSubTypeToMedicineSubTypeResponse);
    }

    @Override
    public MedicineSubTypeResponse findById(Long medicineSubTypeId) {
        MedicineSubType medicineSubType = medicineSubTypeRepository.findById(medicineSubTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("MedicineSubType not found with id: " + medicineSubTypeId));

        return medicineSubTypeMapper.medicineSubTypeToMedicineSubTypeResponse(medicineSubType);    }

    @Override
    public Page<MedicineSubTypeResponse> findAllByNameContaining(String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();

        return medicineSubTypeRepository.findAllByNameContaining(nameContaining, pageable)
                .map(medicineSubTypeMapper::medicineSubTypeToMedicineSubTypeResponse);
    }

    @Override
    public MedicineSubTypeResponse createMedicineSubType(MedicineSubTypeDTO medicineSubTypeDTO) {

        if(medicineSubTypeDTO.getName() != null){
            medicineSubTypeDTO.setName(medicineSubTypeDTO.getName().toLowerCase());
        }

        MedicineSubType medicineSubType = medicineSubTypeMapper.medicineSubTypeDtoToMedicineSubType(medicineSubTypeDTO);

        return medicineSubTypeMapper.medicineSubTypeToMedicineSubTypeResponse(medicineSubTypeRepository.save(medicineSubType));    }
}
