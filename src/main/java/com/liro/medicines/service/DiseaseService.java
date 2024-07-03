package com.liro.medicines.service;

import com.liro.medicines.dto.DiseaseDTO;
import com.liro.medicines.dto.PresentationDTO;
import com.liro.medicines.dto.responses.DiseaseResponse;
import com.liro.medicines.dto.responses.PresentationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiseaseService {
    Page<DiseaseResponse> findAll(Pageable pageable);

    DiseaseResponse findById(Long diseaseId);

    Page<DiseaseResponse> findAllByNameContaining(String nameContaining, Pageable pageable);

    DiseaseResponse createDisease(DiseaseDTO diseaseDTO);
}
