package com.liro.medicines.controllers;

import com.liro.medicines.dto.ApiResponse;
import com.liro.medicines.dto.PresentationDTO;
import com.liro.medicines.dto.responses.PresentationResponse;
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
@RequestMapping("/medicines/presentations")
public class PresentationController {

    private final PresentationService presentationService;

    @Autowired
    public PresentationController(PresentationService presentationService) {
        this.presentationService = presentationService;
    }

    @GetMapping(value = "/{presentationId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PresentationResponse> getPresentation(@PathVariable("presentationId") Long presentationId) {
        return ResponseEntity.ok(presentationService.findById(presentationId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<PresentationResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(presentationService.findAll(pageable));
    }

    @GetMapping(value = "/getByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<PresentationResponse>> getByNameContaining(
            @RequestParam("nameContaining") String nameContaining, Pageable pageable) {
        return ResponseEntity.ok(presentationService.findAllByNameContaining(nameContaining, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createPresentation(@Valid @RequestBody PresentationDTO presentationDTO) {
        PresentationResponse presentationResponse = presentationService.createPresentation(presentationDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/medicines/presentations/{presentationId}")
                .buildAndExpand(presentationResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "Presentation created successfully"));
    }
}

