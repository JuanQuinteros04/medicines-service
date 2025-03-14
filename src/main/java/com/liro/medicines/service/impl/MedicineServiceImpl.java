package com.liro.medicines.service.impl;

import com.liro.medicines.dto.MedicineDTO;
import com.liro.medicines.dto.migrator.MedicineDTOMigrator;
import com.liro.medicines.dto.mappers.MedicineMapper;
import com.liro.medicines.dto.responses.MedicineResponse;
import com.liro.medicines.exceptions.ResourceNotFoundException;
import com.liro.medicines.model.dbentities.*;
import com.liro.medicines.model.enums.AnimalType;
import com.liro.medicines.repositories.*;
import com.liro.medicines.service.MedicineService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

    @Transactional(readOnly = true)
    @Override
    public Page<MedicineResponse> findAll(Pageable pageable) {
        return medicineRepository.findAll(pageable).map(medicineMapper::medicineToMedicineResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public MedicineResponse findById(Long medicineId) {
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id: " + medicineId));

        return medicineMapper.medicineToMedicineResponse(medicine);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<MedicineResponse> findAllByCommercialNameContainingAndMedicineTypeId(String nameContaining, AnimalType animalType,Long medicineTypeId, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();
        Page<Medicine> medicines;

        Specification<Medicine> spec = MedicineSpecifications.getMedicines(nameContaining, animalType, medicineTypeId);
        medicines = medicineRepository.findAll(spec, pageable);
        return medicines.map(medicineMapper::medicineToMedicineResponse);
    }

    @Transactional
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

        if(medicineDTO.getMedicineGroups() != null){
            List<MedicineGroup> medicineGroups = medicineDTO.getMedicineGroups().stream()
                    .map(medicineGroup -> medicineGroupRepository.findById(medicineGroup)
                    .orElseThrow(() -> new ResourceNotFoundException("MedicineGroup not found with id: " + medicineGroup)))
                            .collect(Collectors.toList());

            medicine.setMedicineGroups(medicineGroups);

        }

        if(medicineDTO.getComponents() != null){
            List<Component> components =  medicineDTO.getComponents().stream()
                    .map(component -> componentRepository.findById(component)
                            .orElseThrow(() -> new ResourceNotFoundException("Component not found with id: " + component)))
                    .collect(Collectors.toList());
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

    @Transactional
    @Override
    public void migrateMedicines(List<MedicineDTOMigrator> medicineDTOMigratorList) {


        medicineDTOMigratorList.forEach(medicineDTO -> {

            if (medicineDTO.getCommercialName() != null) {
                medicineDTO.setCommercialName(medicineDTO.getCommercialName().toLowerCase());
            }
            if (medicineDTO.getFormalName() != null) {
                medicineDTO.setFormalName(medicineDTO.getFormalName().toLowerCase());
            }


            String normalizedMedicineTypeName = medicineDTO.getMedicineType().toLowerCase().trim();
            Optional<MedicineType> optionalMedicineType = medicineTypeRepository.findByName(normalizedMedicineTypeName);

            MedicineType medicineType = optionalMedicineType.orElseGet(() -> medicineTypeRepository.save(
                    MedicineType.builder()
                            .name(normalizedMedicineTypeName)
                            .build()
            ));


            String normalizedBrandName = medicineDTO.getBrandName().toLowerCase().trim();
            Optional<Brand> optionalBrand = brandRepository.findByName(normalizedBrandName);

            Brand brand = optionalBrand.orElseGet(() -> brandRepository.save(
                    Brand.builder()
                            .name(normalizedBrandName)
                            .commercialName(normalizedBrandName)
                            .build()
            ));


            String normalizedPresentationName = medicineDTO.getPresentationName().toLowerCase().trim();
            Optional<Presentation> optionalPresentation = presentationRepository.findByName(normalizedPresentationName);

             Presentation presentation = optionalPresentation.orElseGet(() -> presentationRepository.save(
                    Presentation.builder()
                            .name(normalizedPresentationName)
                            .build()
            ));


            Medicine medicine = medicineMapper.medicineDtoMigratorToMedicine(medicineDTO);

            if(medicineDTO.getMedicineGroups() != null){
                List<MedicineGroup> medicineGroups = medicineDTO.getMedicineGroups().stream()
                        .map(medicineGroup -> {

                            String normalizedMedicineGrouo = medicineGroup.toLowerCase().trim();
                            Optional<MedicineGroup> optionalMedicineGroup = medicineGroupRepository.findByName(normalizedMedicineGrouo);

                            return optionalMedicineGroup.orElseGet(() -> medicineGroupRepository.save(
                                    MedicineGroup.builder()
                                            .name(normalizedMedicineGrouo)
                                            .build()
                            ));
                        })
                        .collect(Collectors.toList());

                medicine.setMedicineGroups(medicineGroups);

            }

            if(medicineDTO.getComponents() != null){
                List<Component> components = medicineDTO.getComponents().stream()
                        .map(component -> {

                            String normalizedComponent = component.toLowerCase().trim();


                            Optional<Component> optionalComponent = componentRepository.findByName(normalizedComponent);

                            return optionalComponent.orElseGet(() -> componentRepository.save(
                                    Component.builder()
                                            .name(normalizedComponent)
                                            .build()
                            ));
                        })
                        .collect(Collectors.toList());

                medicine.setComponents(components);

            }

            if (brand.getMedicines() == null) brand.setMedicines(new HashSet<>());
            brand.getMedicines().add(medicine);
            medicine.setBrand(brand);

            medicine.setMedicineType(medicineType);
            medicine.setPresentation(presentation);

             medicineMapper.medicineToMedicineResponse(
                    medicineRepository.save(medicine));
        });
    }
}