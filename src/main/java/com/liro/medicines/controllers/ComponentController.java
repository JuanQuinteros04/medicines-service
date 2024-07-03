package com.liro.medicines.controllers;

import com.liro.medicines.dto.ApiResponse;
import com.liro.medicines.dto.ComponentDTO;
import com.liro.medicines.dto.PresentationDTO;
import com.liro.medicines.dto.responses.ComponentResponse;
import com.liro.medicines.dto.responses.PresentationResponse;
import com.liro.medicines.service.ComponentService;
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
@RequestMapping("/components")
public class ComponentController {

    private final ComponentService componentService;

    @Autowired
    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }

    @GetMapping(value = "/{componentId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ComponentResponse> getComponent(@PathVariable("componentId") Long componentId) {
        return ResponseEntity.ok(componentService.findById(componentId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<ComponentResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(componentService.findAll(pageable));
    }

    @GetMapping(value = "/getByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<ComponentResponse>> getByNameContaining(
            @RequestParam("nameContaining") String nameContaining, Pageable pageable) {
        return ResponseEntity.ok(componentService.findAllByNameContaining(nameContaining, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createComponent(@Valid @RequestBody ComponentDTO componentDTO) {
        ComponentResponse componentResponse = componentService.createComponent(componentDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/medicines/components/{componentId}")
                .buildAndExpand(componentResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "Component created successfully"));
    }
}
