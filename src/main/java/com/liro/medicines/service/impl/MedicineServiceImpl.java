package com.liro.medicines.service.impl;

import com.liro.medicines.dto.MedicineDTO;
import com.liro.medicines.dto.MedicineGroupDTO;
import com.liro.medicines.dto.mappers.MedicineMapper;
import com.liro.medicines.dto.responses.MedicineResponse;
import com.liro.medicines.exceptions.ResourceNotFoundException;
import com.liro.medicines.model.dbentities.*;
import com.liro.medicines.model.enums.AnimalType;
import com.liro.medicines.repositories.*;
import com.liro.medicines.service.MedicineService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;
    private final MedicineMapper medicineMapper;
    private final PresentationRepository presentationRepository;
    private final MedicineTypeRepository medicineTypeRepository;
    private final MedicineGroupRepository medicineGroupRepository;

    private final BrandRepository brandRepository;
    private final ComponentRepository componentRepository;


    public MedicineServiceImpl(MedicineRepository medicineRepository,
                               MedicineMapper medicineMapper,
                               PresentationRepository presentationRepository,
                               MedicineTypeRepository medicineTypeRepository,
                               BrandRepository brandRepository,
                               MedicineGroupRepository medicineGroupRepository,
                               ComponentRepository componentRepository) {
        this.medicineRepository = medicineRepository;
        this.medicineMapper = medicineMapper;
        this.presentationRepository = presentationRepository;
        this.medicineTypeRepository = medicineTypeRepository;
        this.brandRepository = brandRepository;
        this.medicineGroupRepository = medicineGroupRepository;
        this.componentRepository = componentRepository;
    }

    @Override
    public Page<MedicineResponse> findAll(Pageable pageable) {
        return medicineRepository.findAll(pageable).map(medicineMapper::medicineToMedicineResponse);
    }

    @Override
    public MedicineResponse findById(Long medicineId) {
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id: " + medicineId));

        return medicineMapper.medicineToMedicineResponse(medicine);
    }

    @Override
    public Page<MedicineResponse> findAllByCommercialNameContainingAndMedicineTypeId(String nameContaining, AnimalType animalType,Long medicineTypeId, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();
        Page<Medicine> medicines;

        List<AnimalType> animalTypes = (animalType == null || animalType == AnimalType.ALL_TYPE)
                ? List.of(AnimalType.ALL_TYPE)
                : List.of(animalType, AnimalType.ALL_TYPE);

        if (medicineTypeId != null) {
            medicines = medicineRepository.findAllByCommercialNameContainingAndAnimalTypeInAndMedicineTypeId(nameContaining, animalTypes, medicineTypeId, pageable);
            return medicines.map(medicineMapper::medicineToMedicineResponse);
        } else {
            medicines = medicineRepository.findAllByCommercialNameContainingAndAnimalTypeIn(nameContaining, animalTypes, pageable);
            return medicines.map(medicineMapper::medicineToMedicineResponse);
        }



    }

    @Override
    public MedicineResponse createMedicine(MedicineDTO medicineDTO) {
        if (medicineDTO.getCommercialName() != null) {
            medicineDTO.setCommercialName(medicineDTO.getCommercialName().toLowerCase());
        }
        if (medicineDTO.getFormalName() != null) {
            medicineDTO.setFormalName(medicineDTO.getFormalName().toLowerCase());
        }

        Brand brand = brandRepository.findById(medicineDTO.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + medicineDTO.getBrandId()));

        MedicineType medicineType = medicineTypeRepository.findById(medicineDTO.getMedicineTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("MedicineType not found with id: " + medicineDTO.getMedicineTypeId()));



        Presentation presentation = presentationRepository.findById(medicineDTO.getPresentationId())
                .orElseThrow(() -> new ResourceNotFoundException("Presentation not found with id: " + medicineDTO.getPresentationId()));




        Medicine medicine = medicineMapper.medicineDtoToMedicine(medicineDTO);

        if(medicineDTO.getMedicineGroupId() != null){
            MedicineGroup medicineGroup = medicineGroupRepository.findById(medicineDTO.getMedicineGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException("MedicineGroup not found with id: " + medicineDTO.getMedicineGroupId()));
            medicine.setMedicineGroup(medicineGroup);

        }

        if(medicineDTO.getComponents() != null){
            HashSet<Component> components = (HashSet<Component>) medicineDTO.getComponents().stream()
                    .map(component -> componentRepository.findById(component)
                            .orElseThrow(() -> new ResourceNotFoundException("Component not found with id: " + component)))
                    .collect(Collectors.toSet());
            medicine.setComponents(components);

        }

        if (brand.getMedicines() == null) brand.setMedicines(new HashSet<>());
        brand.getMedicines().add(medicine);
        medicine.setBrand(brand);

        medicine.setMedicineType(medicineType);
        medicine.setPresentation(presentation);

        return medicineMapper.medicineToMedicineResponse(
                medicineRepository.save(medicine)
        );
    }
}