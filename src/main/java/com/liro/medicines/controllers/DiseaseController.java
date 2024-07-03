package com.liro.medicines.controllers;

import com.liro.medicines.dto.ApiResponse;
import com.liro.medicines.dto.DiseaseDTO;
import com.liro.medicines.dto.PresentationDTO;
import com.liro.medicines.dto.responses.DiseaseResponse;
import com.liro.medicines.dto.responses.PresentationResponse;
import com.liro.medicines.service.DiseaseService;
import com.liro.medicines.service.PresentationService;
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
@RequestMapping("/diseases")
public class DiseaseController {

    private final DiseaseService diseaseService;

    @Autowired
    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @GetMapping(value = "/{diseaseId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DiseaseResponse> getDisease(@PathVariable("diseaseId") Long diseaseId) {
        return ResponseEntity.ok(diseaseService.findById(diseaseId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<DiseaseResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(diseaseService.findAll(pageable));
    }

    @GetMapping(value = "/getByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<DiseaseResponse>> getByNameContaining(
            @RequestParam("nameContaining") String nameContaining, Pageable pageable) {
        return ResponseEntity.ok(diseaseService.findAllByNameContaining(nameContaining, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createDisease(@Valid @RequestBody DiseaseDTO diseaseDTO) {
        DiseaseResponse diseaseResponse = diseaseService.createDisease(diseaseDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/medicines/diseases/{diseaseId}")
                .buildAndExpand(diseaseResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "Disease created successfully"));
    }
}
