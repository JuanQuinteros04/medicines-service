package com.liro.medicines.service.impl;

import com.liro.medicines.config.FeignAnimalClient;
import com.liro.medicines.dto.ApplicationRecordDTO;
import com.liro.medicines.dto.UserDTO;
import com.liro.medicines.dto.mappers.ApplicationRecordMapper;
import com.liro.medicines.dto.migrator.ApplicationRecordDTOMigrator;
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
    public ApplicationRecordResponse createApplicationRecord(ApplicationRecordDTO applicationRecordDTO, String token, Long clinicId) {
        feignAnimalClient.hasPermissions(applicationRecordDTO.getAnimalId(), false, false, false, clinicId, token);

        ApplicationRecord applicationRecord = applicationRecordMapper.applicationRecordDtoToApplicationRecord(applicationRecordDTO);


        Medicine medicine = medicineRepository.findById(applicationRecordDTO.getMedicineId())
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id: " + applicationRecordDTO.getMedicineId()));

        applicationRecord.setMedicine(medicine);

        UserDTO userDTO =  getUser(token);

        if (userDTO.getRoles().contains("ROLE_VET")) {
            applicationRecord.setValid(true);
            applicationRecord.setVetProfileId(userDTO.getId());
            applicationRecord.setVetClinicId(clinicId);
        }

        return applicationRecordMapper.applicationRecordToApplicationRecordResponse(
                applicationRecordRepository.save(applicationRecord)
        );
    }

    @Override
    public void migrateApplicationRecord(Long vetProfileId, Long vetClincId, List<ApplicationRecordDTOMigrator> applicationRecordDTOMigrators) {
            applicationRecordDTOMigrators.stream().parallel().forEach(applicationRecordDTOMigrator -> {

                try {
                    ApplicationRecord applicationRecord = applicationRecordMapper.applicationRecordDTOMigratorToApplicationRecord(applicationRecordDTOMigrator);
                    applicationRecord.setDetails(null);
                    applicationRecord.setLote(null);
                    applicationRecord.setControlNumber(null);
                    applicationRecord.setValid(true);
                    applicationRecord.setQuantity(null);
                    applicationRecord.setVetProfileId(vetProfileId);
                    applicationRecord.setVetClinicId(vetClincId);

                    applicationRecordRepository.save(applicationRecord);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            });
    }

        @Override
    public ApplicationRecordResponse getLatestApplicationsForEachMedicineGroup(Long animalId, Long medicineGroupId) {
        return applicationRecordMapper.applicationRecordToApplicationRecordResponse(
                applicationRecordRepository.findTopByAnimalIdAndMedicine_MedicineGroups_IdOrderByApplicationDateDesc(animalId, medicineGroupId)
                        .orElseThrow(() -> new ResourceNotFoundException("Application not found")));
    }

    @Override
    public ApplicationRecordResponse getLatestApplicationsForEachMedicineType(Long animalId, Long medicineTypeId) {
        return applicationRecordMapper.applicationRecordToApplicationRecordResponse(
                applicationRecordRepository.findTopByAnimalIdAndMedicineMedicineTypeIdOrderByApplicationDateDesc(animalId, medicineTypeId)
                        .orElseThrow(() -> new ResourceNotFoundException("Application not found")));
    }

    @Override
    public Page<ApplicationRecordResponse> findAllByAnimalIdAndMedicineMedicineGroupId(Pageable pageable, Long animalId, Long medicineGroupId) {
        return applicationRecordRepository.findAllByAnimalIdAndMedicine_MedicineGroups_Id( animalId, medicineGroupId, pageable)
                .map(applicationRecordMapper::applicationRecordToApplicationRecordResponse);
    }

    @Override
    public Page<ApplicationRecordResponse> findAllByAnimalIdAndMedicineMedicineTypeId(Pageable pageable, Long animalId, Long medicineTypeId) {
        return applicationRecordRepository.findAllByAnimalIdAndMedicineMedicineTypeId(animalId, medicineTypeId, pageable)
                .map(applicationRecordMapper::applicationRecordToApplicationRecordResponse);
    }
}
