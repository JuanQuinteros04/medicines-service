package com.liro.medicines.service.impl;

import com.liro.medicines.dto.PresentationDTO;
import com.liro.medicines.dto.mappers.PresentationMapper;
import com.liro.medicines.dto.responses.PresentationResponse;
import com.liro.medicines.exceptions.ResourceNotFoundException;
import com.liro.medicines.model.dbentities.Medicine;
import com.liro.medicines.model.dbentities.Presentation;
import com.liro.medicines.repositories.MedicineRepository;
import com.liro.medicines.repositories.PresentationRepository;
import com.liro.medicines.service.PresentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PresentationServiceImpl implements PresentationService {

    private final PresentationRepository presentationRepository;
    private final PresentationMapper presentationMapper;
    private final MedicineRepository medicineRepository;

    @Autowired
    public PresentationServiceImpl(PresentationRepository presentationRepository,
                                   PresentationMapper presentationMapper,
                                   MedicineRepository medicineRepository) {
        this.presentationRepository = presentationRepository;
        this.presentationMapper = presentationMapper;
        this.medicineRepository = medicineRepository;
    }

    @Override
    public Page<PresentationResponse> findAll(Pageable pageable) {
        return presentationRepository.findAll(pageable).map(presentationMapper::presentationToPresentationResponse);
    }

    @Override
    public PresentationResponse findById(Long presentationId) {
        Presentation presentation = presentationRepository.findById(presentationId)
                .orElseThrow(() -> new ResourceNotFoundException("Presentation not found with id: " + presentationId));

        return presentationMapper.presentationToPresentationResponse(presentation);
    }

    @Override
    public Page<PresentationResponse> findAllByNameContaining(String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();

        return presentationRepository.findAllByNameContaining(nameContaining, pageable)
                .map(presentationMapper::presentationToPresentationResponse);
    }

    @Override
    public PresentationResponse createPresentation(PresentationDTO presentationDTO) {
        if (presentationDTO.getName() != null) {
            presentationDTO.setName(presentationDTO.getName().toLowerCase());
        }

        Medicine medicine = medicineRepository.findById(presentationDTO.getMedicineId())
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id: "
                        + presentationDTO.getMedicineId()));

        Presentation presentation = presentationMapper.presentationDtoToPresentation(presentationDTO);

        medicine.setPresentation(presentation);
        presentation.setMedicine(medicine);

        return presentationMapper.presentationToPresentationResponse(
                presentationRepository.save(presentation)
        );
    }
}
