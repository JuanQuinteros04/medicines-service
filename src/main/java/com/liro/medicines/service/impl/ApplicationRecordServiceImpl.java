package com.liro.medicines.service.impl;

import com.liro.medicines.config.FeignAnimalClient;
import com.liro.medicines.dto.ApplicationRecordDTO;
import com.liro.medicines.dto.UserDTO;
import com.liro.medicines.dto.mappers.ApplicationRecordMapper;
import com.liro.medicines.dto.responses.ApplicationRecordResponse;
import com.liro.medicines.exceptions.ResourceNotFoundException;
import com.liro.medicines.model.dbentities.ApplicationRecord;
import com.liro.medicines.model.dbentities.Medicine;
import com.liro.medicines.repositories.*;
import com.liro.medicines.service.ApplicationRecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.liro.medicines.utils.Util.getUser;


@Service
public class ApplicationRecordServiceImpl implements ApplicationRecordService {

    private final ApplicationRecordRepository applicationRecordRepository;
    private final MedicineRepository medicineRepository;

    private final ApplicationRecordMapper applicationRecordMapper;
    private final PresentationRepository presentationRepository;
    private final MedicineTypeRepository medicineTypeRepository;
    private final MedicineGroupRepository medicineGroupRepository;

    private final BrandRepository brandRepository;
    private final ComponentRepository componentRepository;
    private final FeignAnimalClient feignAnimalClient;



    public ApplicationRecordServiceImpl(ApplicationRecordRepository applicationRecordRepository,
                                        ApplicationRecordMapper applicationRecordMapper,
                                        PresentationRepository presentationRepository,
                                        MedicineTypeRepository medicineTypeRepository,
                                        BrandRepository brandRepository,
                                        MedicineGroupRepository medicineGroupRepository,
                                        ComponentRepository componentRepository,
                                        MedicineRepository medicineRepository,
                                        FeignAnimalClient feignAnimalClient) {
        this.applicationRecordRepository = applicationRecordRepository;
        this.applicationRecordMapper = applicationRecordMapper;
        this.presentationRepository = presentationRepository;
        this.medicineTypeRepository = medicineTypeRepository;
        this.brandRepository = brandRepository;
        this.medicineGroupRepository = medicineGroupRepository;
        this.componentRepository = componentRepository;
        this.medicineRepository  = medicineRepository;
        this.feignAnimalClient = feignAnimalClient;

    }

    @Override
    public Page<ApplicationRecordResponse> findAll(Pageable pageable) {
        return applicationRecordRepository.findAll(pageable).map(applicationRecordMapper::applicationRecordToApplicationRecordResponse);
    }

    @Override
    public ApplicationRecordResponse findById(Long applicationRecordId) {
        ApplicationRecord applicationRecord = applicationRecordRepository.findById(applicationRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("ApplicationRecord not found with id: " + applicationRecordId));

        return applicationRecordMapper.applicationRecordToApplicationRecordResponse(applicationRecord);
    }


    @Override
    public ApplicationRecordResponse createApplicationRecord(ApplicationRecordDTO applicationRecordDTO, String token) {
        feignAnimalClient.hasPermissions(applicationRecordDTO.getAnimalId(), true, false, true, false, token);

        ApplicationRecord applicationRecord = applicationRecordMapper.applicationRecordDtoToApplicationRecord(applicationRecordDTO);


        Medicine medicine = medicineRepository.findById(applicationRecordDTO.getMedicineId())
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id: " + applicationRecordDTO.getMedicineId()));

        applicationRecord.setMedicine(medicine);

        UserDTO userDTO =  getUser(token);

        if (userDTO.getRoles().contains("ROLE_VET")) {
            applicationRecord.setValid(true);
            applicationRecord.setVetProfileId(userDTO.getId());
        }

        return applicationRecordMapper.applicationRecordToApplicationRecordResponse(
                applicationRecordRepository.save(applicationRecord)
        );
    }


    public List<ApplicationRecordResponse> getLatestApplicationsForEachMedicineGroup(Long animalId, Long medicineTypeId) {
        return applicationRecordRepository.findLatestApplicationsForEachMedicineGroupByAnimalAndType(animalId, medicineTypeId)
                .stream().map(applicationRecordMapper::applicationRecordToApplicationRecordResponse).collect(Collectors.toList());
    }
}
