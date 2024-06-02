package com.liro.medicines.controllers;

import com.liro.medicines.dto.ApiResponse;
import com.liro.medicines.dto.MedicineSubTypeDTO;
import com.liro.medicines.dto.responses.MedicineSubTypeResponse;
import com.liro.medicines.service.MedicineSubTypeService;
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
@RequestMapping("/medicineSubType")
public class MedicineSubTypeController {

    private final MedicineSubTypeService medicineSubTypeService;

    @Autowired
    public MedicineSubTypeController(MedicineSubTypeService medicineSubTypeService) {
        this.medicineSubTypeService = medicineSubTypeService;
    }

    @GetMapping(value = "/{medicineSubTypeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MedicineSubTypeResponse> getMedicineSubType(@PathVariable("medicineSubTypeId") Long medicineSubTypeId) {
        return ResponseEntity.ok(medicineSubTypeService.findById(medicineSubTypeId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<MedicineSubTypeResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(medicineSubTypeService.findAll(pageable));
    }

    @GetMapping(value = "/getByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<MedicineSubTypeResponse>> getByNameContaining(
            @RequestParam("nameContaining") String nameContaining, Pageable pageable) {
        return ResponseEntity.ok(medicineSubTypeService.findAllByNameContaining(nameContaining, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createMedicineSubType(@Valid @RequestBody MedicineSubTypeDTO medicineSubTypeDTO) {
        MedicineSubTypeResponse medicineSubTypeResponse = medicineSubTypeService.createMedicineSubType(medicineSubTypeDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/medicineSubType/{medicineSubTypeId}")
                .buildAndExpand(medicineSubTypeResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "MedicineSubType created successfully"));
    }
}
