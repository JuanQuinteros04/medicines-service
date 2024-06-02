package com.liro.medicines.service;

import com.liro.medicines.dto.BrandDTO;
import com.liro.medicines.dto.responses.BrandResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BrandService {

    Page<BrandResponse> findAll(Pageable pageable);

    BrandResponse findById(Long brandId);

    Page<BrandResponse> findAllByNameContaining(String nameContaining, Pageable pageable);

    BrandResponse createBrand(BrandDTO brandDTO);

}
