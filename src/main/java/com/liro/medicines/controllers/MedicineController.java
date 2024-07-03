package com.liro.medicines.controllers;

import com.liro.medicines.dto.ApiResponse;
import com.liro.medicines.dto.MedicineDTO;
import com.liro.medicines.dto.responses.MedicineResponse;
import com.liro.medicines.service.MedicineService;
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
@RequestMapping("/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    @Autowired
    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @GetMapping(value = "/{medicineId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MedicineResponse> getMedicine(@PathVariable("medicineId") Long medicineId) {
        return ResponseEntity.ok(medicineService.findById(medicineId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<MedicineResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(medicineService.findAll(pageable));
    }

    @GetMapping(value = "/getByCommercialNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<MedicineResponse>> getByNameContaining(
            @RequestParam("commercialNameContaining") String nameContaining, Pageable pageable) {
        return ResponseEntity.ok(medicineService.findAllByCommercialNameContaining(nameContaining, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createMedicine(@Valid @RequestBody MedicineDTO medicineDTO) {
        MedicineResponse medicineResponse = medicineService.createMedicine(medicineDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/medicines/{medicineId}")
                .buildAndExpand(medicineResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "Medicine created successfully"));
    }

}