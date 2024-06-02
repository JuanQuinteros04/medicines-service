package com.liro.medicines.service;

import com.liro.medicines.dto.PresentationDTO;
import com.liro.medicines.dto.responses.PresentationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PresentationService {

    Page<PresentationResponse> findAll(Pageable pageable);

    PresentationResponse findById(Long presentationId);

    Page<PresentationResponse> findAllByNameContaining(String nameContaining, Pageable pageable);

    PresentationResponse createPresentation(PresentationDTO presentationDTO);


}
