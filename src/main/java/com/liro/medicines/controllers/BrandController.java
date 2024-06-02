package com.liro.medicines.controllers;
import com.liro.medicines.dto.ApiResponse;
import com.liro.medicines.dto.BrandDTO;
import com.liro.medicines.dto.responses.BrandResponse;
import com.liro.medicines.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/brands")
public class BrandController {

        private final BrandService brandService;

        @Autowired
        public BrandController(BrandService brandService) {
            this.brandService = brandService;
        }

    @GetMapping(value = "/{brandId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BrandResponse> getBrand(
            @PathVariable("brandId") Long brandId) {
        return ResponseEntity.ok(brandService.findById(brandId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<BrandResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(brandService.findAll(pageable));
    }

    @GetMapping(value = "/getByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<BrandResponse>> getByNameContaining(
            @RequestParam("nameContaining") String nameContaining, Pageable pageable) {
        return ResponseEntity.ok(brandService.findAllByNameContaining(nameContaining, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createBrand(@Valid @RequestBody BrandDTO brandDto) {
        BrandResponse brandResponse = brandService.createBrand(brandDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/brands/{brandId}")
                .buildAndExpand(brandResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "Brand created successfully"));
    }
}
