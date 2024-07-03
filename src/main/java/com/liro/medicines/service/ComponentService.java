package com.liro.medicines.service;

import com.liro.medicines.dto.ComponentDTO;
import com.liro.medicines.dto.PresentationDTO;
import com.liro.medicines.dto.responses.ComponentResponse;
import com.liro.medicines.dto.responses.PresentationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ComponentService {
    Page<ComponentResponse> findAll(Pageable pageable);

    ComponentResponse findById(Long componentId);

    Page<ComponentResponse> findAllByNameContaining(String nameContaining, Pageable pageable);

    ComponentResponse createComponent(ComponentDTO componentDTO);
}
