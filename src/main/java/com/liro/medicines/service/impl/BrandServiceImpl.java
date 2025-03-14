package com.liro.medicines.service.impl;

import com.liro.medicines.dto.BrandDTO;
import com.liro.medicines.dto.mappers.BrandMapper;
import com.liro.medicines.dto.responses.BrandResponse;
import com.liro.medicines.exceptions.ResourceNotFoundException;
import com.liro.medicines.model.dbentities.Brand;
import com.liro.medicines.repositories.BrandRepository;
import com.liro.medicines.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository, BrandMapper brandMapper){
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }


    @Transactional(readOnly = true)
    @Override
    public Page<BrandResponse> findAll(Pageable pageable) {
        return brandRepository.findAll(pageable).map(brandMapper::brandToBrandResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public BrandResponse findById(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + brandId));
        return brandMapper.brandToBrandResponse(brand);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BrandResponse> findAllByNameContaining(String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();

        return brandRepository.findAllByNameContaining(nameContaining, pageable)
                .map(brandMapper::brandToBrandResponse);
    }

    @Transactional
    @Override
    public BrandResponse createBrand(BrandDTO brandDTO) {

        if (brandDTO.getName() != null) {
            brandDTO.setName(brandDTO.getName().toLowerCase());
        }
        if (brandDTO.getCommercialName() != null) {
            brandDTO.setCommercialName(brandDTO.getCommercialName().toLowerCase());
        }

        Brand brand = brandMapper.brandDtoToBrand(brandDTO);

        return brandMapper.brandToBrandResponse(
                brandRepository.save(brand)
        );
    }
}


