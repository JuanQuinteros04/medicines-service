package com.liro.medicines.controllers;

import com.liro.medicines.dto.ApiResponse;
import com.liro.medicines.dto.ApplicationRecordDTO;
import com.liro.medicines.dto.migrator.ApplicationRecordDTOMigrator;
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
    public ResponseEntity<Page<ApplicationRecordResponse>> getAll(Pageable pageable,
                                                                  @RequestParam("animalId") Long animalId,
                                                                  @RequestParam(name = "medicineGroupId", required = false) Long medicineGroupId,
                                                                  @RequestParam(name = "medicineTypeId", required = false) Long medicineTypeId) {
        if(medicineGroupId != null){
            return ResponseEntity.ok(applicationRecordService.findAllByAnimalIdAndMedicineMedicineGroupId(pageable, animalId, medicineGroupId));
        } else if (medicineTypeId != null) {
            return ResponseEntity.ok(applicationRecordService.findAllByAnimalIdAndMedicineMedicineTypeId(pageable, animalId, medicineTypeId));
        }else{
            return ResponseEntity.ok(applicationRecordService.findAll(pageable));

        }
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createApplicationRecord(@Valid @RequestBody ApplicationRecordDTO applicationRecordDto,
                                                               @RequestHeader(name = "clinicId", required = false) Long clinicId,
                                                               @RequestHeader(name = "Authorization", required = false) String token) {
        ApplicationRecordResponse applicationRecordResponse = applicationRecordService.createApplicationRecord(applicationRecordDto, token, clinicId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/applicationRecords/{applicationRecordId}")
                .buildAndExpand(applicationRecordResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "ApplicationRecord created successfully"));
    }

    @PutMapping(value = "/{applicationRecordId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApplicationRecordResponse> updateApplicationRecord(@Valid @RequestBody ApplicationRecordDTO applicationRecordDto,
                                                               @PathVariable("applicationRecordId") Long applicationRecordId,
                                                               @RequestHeader(name = "clinicId", required = false) Long clinicId,
                                                               @RequestHeader(name = "Authorization", required = false) String token) {
        ApplicationRecordResponse applicationRecordResponse = applicationRecordService.updateApplicationRecord(applicationRecordDto, token, clinicId, applicationRecordId);


        return ResponseEntity.ok(applicationRecordResponse);
    }

   /* @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> migrateApplicationRecord(@Valid @RequestBody List<ApplicationRecordDTOMigrator> applicationRecordDTOMigrators,
                                                                @RequestParam("vetClinicId") Long vetClinicId,
                                                                @RequestParam(name = "vetUserId") Long vetUserId){

        applicationRecordService.migrateApplicationRecord(vetUserId, vetClinicId, applicationRecordDTOMigrators);

        return ResponseEntity.ok().build();
    }*/

    @GetMapping(value = "/latest-per-group", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApplicationRecordResponse> getLatestApplicationsForEachMedicineGroup(@RequestParam("animalId") Long animalId,
                                                                                               @RequestParam("medicineGroupId") Long medicineGroupId) {
        ApplicationRecordResponse applicationRecords = applicationRecordService.getLatestApplicationsForEachMedicineGroup(animalId, medicineGroupId);
        return ResponseEntity.ok(applicationRecords);
    }

    @GetMapping(value = "/latest-per-type", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApplicationRecordResponse> getLatestApplicationsForEachMedicineType(@RequestParam("animalId") Long animalId,
                                                                                              @RequestParam("medicineTypeId") Long medicineTypeId) {
        ApplicationRecordResponse applicationRecords = applicationRecordService.getLatestApplicationsForEachMedicineType(animalId, medicineTypeId);
        return ResponseEntity.ok(applicationRecords);
    }
}
