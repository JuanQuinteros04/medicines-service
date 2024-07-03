package com.liro.medicines.service.impl;


import com.liro.medicines.dto.MedicineGroupDTO;
import com.liro.medicines.dto.mappers.MedicineGroupMapper;
import com.liro.medicines.dto.responses.MedicineGroupResponse;
import com.liro.medicines.model.dbentities.MedicineGroup;
import com.liro.medicines.repositories.MedicineGroupRepository;
import com.liro.medicines.service.MedicineGroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.liro.medicines.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MedicineGroupServiceImpl implements MedicineGroupService {

    private final MedicineGroupRepository medicineGroupRepository;
    private final MedicineGroupMapper medicineGroupMapper;

    public MedicineGroupServiceImpl(MedicineGroupRepository medicineGroupRepository, MedicineGroupMapper medicineGroupMapper) {
        this.medicineGroupRepository = medicineGroupRepository;
        this.medicineGroupMapper = medicineGroupMapper;
    }

    @Override
    public Page<MedicineGroupResponse> findAll(Pageable pageable) {
        return medicineGroupRepository.findAll(pageable).map(medicineGroupMapper::medicineGroupToMedicineGroupResponse);
    }

    @Override
    public MedicineGroupResponse findById(Long medicineGroupId) {
        MedicineGroup medicineGroup = medicineGroupRepository.findById(medicineGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("MedicineGroup not found with id: " + medicineGroupId));

        return medicineGroupMapper.medicineGroupToMedicineGroupResponse(medicineGroup);    }

    @Override
    public Page<MedicineGroupResponse> findAllByNameContaining(String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();

        return medicineGroupRepository.findAllByNameContaining(nameContaining, pageable)
                .map(medicineGroupMapper::medicineGroupToMedicineGroupResponse);
    }

    @Override
    public MedicineGroupResponse createMedicineGroup(MedicineGroupDTO medicineGroupDTO) {

        if(medicineGroupDTO.getName() != null){
            medicineGroupDTO.setName(medicineGroupDTO.getName().toLowerCase());
        }

        MedicineGroup medicineGroup = medicineGroupMapper.medicineGroupDTOToMedicineGroup(medicineGroupDTO);

        return medicineGroupMapper.medicineGroupToMedicineGroupResponse(medicineGroupRepository.save(medicineGroup));    }
}
