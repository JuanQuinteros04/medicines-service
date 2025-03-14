package com.liro.medicines.service.impl;

import com.liro.medicines.dto.MedicineTypeDTO;
import com.liro.medicines.dto.mappers.MedicineTypeMapper;
import com.liro.medicines.dto.responses.MedicineTypeResponse;
import com.liro.medicines.model.dbentities.MedicineType;
import com.liro.medicines.repositories.MedicineTypeRepository;
import com.liro.medicines.service.MedicineTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.liro.medicines.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MedicineTypeServiceImpl implements MedicineTypeService {

    private final MedicineTypeRepository medicineTypeRepository;
    private final MedicineTypeMapper medicineTypeMapper;

    public MedicineTypeServiceImpl(MedicineTypeRepository medicineTypeRepository, MedicineTypeMapper medicineTypeMapper) {
        this.medicineTypeRepository = medicineTypeRepository;
        this.medicineTypeMapper = medicineTypeMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<MedicineTypeResponse> findAll(Pageable pageable) {
        return medicineTypeRepository.findAll(pageable).map(medicineTypeMapper::medicineTypeToMedicineTypeResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public MedicineTypeResponse findById(Long medicineTypeId) {
        MedicineType medicineType = medicineTypeRepository.findById(medicineTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("MedicineType not found with id: " + medicineTypeId));

        return medicineTypeMapper.medicineTypeToMedicineTypeResponse(medicineType);    }

    @Transactional(readOnly = true)
    @Override
    public Page<MedicineTypeResponse> findAllByNameContaining(String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();

        return medicineTypeRepository.findAllByNameContaining(nameContaining, pageable)
                .map(medicineTypeMapper::medicineTypeToMedicineTypeResponse);
    }

    @Transactional
    @Override
    public MedicineTypeResponse createMedicineType(MedicineTypeDTO medicineTypeDTO) {

        if(medicineTypeDTO.getName() != null){
            medicineTypeDTO.setName(medicineTypeDTO.getName().toLowerCase());
        }

        MedicineType medicineType = medicineTypeMapper.medicineTypeDTOToMedicineType(medicineTypeDTO);

        return medicineTypeMapper.medicineTypeToMedicineTypeResponse(medicineTypeRepository.save(medicineType));    }
}
