package com.liro.medicines.controllers;
import com.liro.medicines.dto.ApiResponse;
import com.liro.medicines.dto.ApplicationRecordDTO;
import com.liro.medicines.dto.responses.ApplicationRecordResponse;
import com.liro.medicines.service.ApplicationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/applicationRecords")
public class ApplicationRecordController {

    private final ApplicationRecordService applicationRecordService;

    @Autowired
    public ApplicationRecordController(ApplicationRecordService applicationRecordService) {
        this.applicationRecordService = applicationRecordService;
    }

    @GetMapping(value = "/{applicationRecordId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApplicationRecordResponse> getApplicationRecord(
            @PathVariable("applicationRecordId") Long applicationRecordId) {
        return ResponseEntity.ok(applicationRecordService.findById(applicationRecordId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<ApplicationRecordResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(applicationRecordService.findAll(pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createApplicationRecord(@Valid @RequestBody ApplicationRecordDTO applicationRecordDto,
                                                               @RequestHeader(name = "Authorization", required = false) String token) {
        ApplicationRecordResponse applicationRecordResponse = applicationRecordService.createApplicationRecord(applicationRecordDto, token);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/applicationRecords/{applicationRecordId}")
                .buildAndExpand(applicationRecordResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "ApplicationRecord created successfully"));
    }

    @GetMapping(value = "/latest-per-group", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ApplicationRecordResponse>> getLatestApplicationsForEachMedicineGroup(@RequestParam("animalId") Long animalId,
                                                                                                     @RequestParam("medicineTypeId") Long medicineTypeId) {
        List<ApplicationRecordResponse> applicationRecords = applicationRecordService.getLatestApplicationsForEachMedicineGroup(animalId, medicineTypeId);
        return ResponseEntity.ok(applicationRecords);
    }
}
