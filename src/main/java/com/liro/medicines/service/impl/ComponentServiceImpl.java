package com.liro.medicines.service.impl;


import com.liro.medicines.dto.ComponentDTO;
import com.liro.medicines.dto.mappers.ComponentMapper;
import com.liro.medicines.dto.responses.ComponentResponse;
import com.liro.medicines.model.dbentities.Component;
import com.liro.medicines.repositories.ComponentRepository;
import com.liro.medicines.service.ComponentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.liro.medicines.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComponentServiceImpl implements ComponentService {

    private final ComponentRepository componentRepository;

    private final ComponentMapper componentMapper;

    public ComponentServiceImpl(ComponentRepository componentRepository,  ComponentMapper componentMapper) {
        this.componentRepository = componentRepository;
        this.componentMapper = componentMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ComponentResponse> findAll(Pageable pageable) {
        return componentRepository.findAll(pageable).map(componentMapper::componentTocomponentResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public ComponentResponse findById(Long componentId) {
        Component component = componentRepository.findById(componentId)
                .orElseThrow(() -> new ResourceNotFoundException("Component not found with id: " + componentId));

        return componentMapper.componentTocomponentResponse(component);    }

    @Transactional(readOnly = true)
    @Override
    public Page<ComponentResponse> findAllByNameContaining(String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();

        return componentRepository.findAllByNameContaining(nameContaining, pageable)
                .map(componentMapper::componentTocomponentResponse);
    }

    @Transactional
    @Override
    public ComponentResponse createComponent(ComponentDTO componentDTO) {

        if(componentDTO.getName() != null){
            componentDTO.setName(componentDTO.getName().toLowerCase());
        }

        Component component = componentMapper.componentDtoToComponent(componentDTO);

        return componentMapper.componentTocomponentResponse(componentRepository.save(component));    }
}
