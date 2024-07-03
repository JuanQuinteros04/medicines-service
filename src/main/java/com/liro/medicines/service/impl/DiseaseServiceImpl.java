package com.liro.medicines.service.impl;


import com.liro.medicines.dto.DiseaseDTO;
import com.liro.medicines.dto.mappers.DiseaseMapper;
import com.liro.medicines.dto.responses.DiseaseResponse;
import com.liro.medicines.model.dbentities.Disease;
import com.liro.medicines.repositories.DiseaseRepository;
import com.liro.medicines.service.DiseaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.liro.medicines.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DiseaseServiceImpl implements DiseaseService {

    private final DiseaseRepository diseaseRepository;
    private final DiseaseMapper diseaseMapper;

    public DiseaseServiceImpl(DiseaseRepository diseaseRepository, DiseaseMapper diseaseMapper) {
        this.diseaseRepository = diseaseRepository;
        this.diseaseMapper = diseaseMapper;
    }

    @Override
    public Page<DiseaseResponse> findAll(Pageable pageable) {
        return diseaseRepository.findAll(pageable).map(diseaseMapper::diseaseToDiseaseResponse);
    }

    @Override
    public DiseaseResponse findById(Long diseaseId) {
        Disease disease = diseaseRepository.findById(diseaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Disease not found with id: " + diseaseId));

        return diseaseMapper.diseaseToDiseaseResponse(disease);    }

    @Override
    public Page<DiseaseResponse> findAllByNameContaining(String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();

        return diseaseRepository.findAllByNameContaining(nameContaining, pageable)
                .map(diseaseMapper::diseaseToDiseaseResponse);
    }

    @Override
    public DiseaseResponse createDisease(DiseaseDTO diseaseDTO) {

        if(diseaseDTO.getName() != null){
            diseaseDTO.setName(diseaseDTO.getName().toLowerCase());
        }

        Disease disease = diseaseMapper.diseaseDtoToDisease(diseaseDTO);

        return diseaseMapper.diseaseToDiseaseResponse(diseaseRepository.save(disease));    }
}
