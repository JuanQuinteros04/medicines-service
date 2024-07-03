package com.liro.medicines.controllers;

import com.liro.medicines.dto.ApiResponse;
import com.liro.medicines.dto.MedicineGroupDTO;
import com.liro.medicines.dto.PresentationDTO;
import com.liro.medicines.dto.responses.MedicineGroupResponse;
import com.liro.medicines.dto.responses.PresentationResponse;
import com.liro.medicines.service.MedicineGroupService;
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
@RequestMapping("/medicinesGroups")
public class MedicineGroupController {

    private final MedicineGroupService medicineGroupService;

    @Autowired
    public MedicineGroupController(MedicineGroupService medicineGroupService) {
        this.medicineGroupService = medicineGroupService;
    }

    @GetMapping(value = "/{medicineGroupId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MedicineGroupResponse> getMedicineGroup(@PathVariable("medicineGroupId") Long medicineGroupId) {
        return ResponseEntity.ok(medicineGroupService.findById(medicineGroupId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<MedicineGroupResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(medicineGroupService.findAll(pageable));
    }

    @GetMapping(value = "/getByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<MedicineGroupResponse>> getByNameContaining(
            @RequestParam("nameContaining") String nameContaining, Pageable pageable) {
        return ResponseEntity.ok(medicineGroupService.findAllByNameContaining(nameContaining, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createMedicineGroup(@Valid @RequestBody MedicineGroupDTO medicineGroupDTO) {
        MedicineGroupResponse medicineGroupResponse = medicineGroupService.createMedicineGroup(medicineGroupDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/medicines/medicineGroups/{medicineGroupId}")
                .buildAndExpand(medicineGroupResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "MedicineGroup created successfully"));
    }
}
