package com.liro.medicines.controllers;

import com.liro.medicines.dto.ApiResponse;
import com.liro.medicines.dto.MedicineTypeDTO;
import com.liro.medicines.dto.PresentationDTO;
import com.liro.medicines.dto.responses.MedicineTypeResponse;
import com.liro.medicines.dto.responses.PresentationResponse;
import com.liro.medicines.service.MedicineTypeService;
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
@RequestMapping("/medicinesTypes")
public class MedicineTypeController {

    private final MedicineTypeService medicineTypeService;

    @Autowired
    public MedicineTypeController(MedicineTypeService medicineTypeService) {
        this.medicineTypeService = medicineTypeService;
    }

    @GetMapping(value = "/{medicineTypeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MedicineTypeResponse> getMedicineType(@PathVariable("medicineTypeId") Long medicineTypeId) {
        return ResponseEntity.ok(medicineTypeService.findById(medicineTypeId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<MedicineTypeResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(medicineTypeService.findAll(pageable));
    }

    @GetMapping(value = "/getByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<MedicineTypeResponse>> getByNameContaining(
            @RequestParam("nameContaining") String nameContaining, Pageable pageable) {
        return ResponseEntity.ok(medicineTypeService.findAllByNameContaining(nameContaining, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createMedicineType(@Valid @RequestBody MedicineTypeDTO medicineTypeDTO) {
        MedicineTypeResponse medicineTypeResponse = medicineTypeService.createMedicineType(medicineTypeDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/medicines/medicineTypes/{medicineTypeId}")
                .buildAndExpand(medicineTypeResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "MedicineType created successfully"));
    }
}
