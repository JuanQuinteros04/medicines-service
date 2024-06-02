package com.liro.medicines.controllers;

import com.liro.medicines.dto.ApiResponse;
import com.liro.medicines.dto.FormulaDTO;
import com.liro.medicines.dto.responses.FormulaResponse;
import com.liro.medicines.service.FormulaService;
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
@RequestMapping("/formulas")
public class FormulaController {

    private final FormulaService formulaService;

    @Autowired
    public FormulaController(FormulaService formulaService) {
        this.formulaService = formulaService;
    }

    @GetMapping(value = "/{formulaId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<FormulaResponse> getFormula(@PathVariable("formulaId") Long formulaId) {
        return ResponseEntity.ok(formulaService.findById(formulaId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<FormulaResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(formulaService.findAll(pageable));
    }

    @GetMapping(value = "/getByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<FormulaResponse>> getByNameContaining(
            @RequestParam("nameContaining") String nameContaining, Pageable pageable) {
        return ResponseEntity.ok(formulaService.findAllByNameContaining(nameContaining, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createFormula(@Valid @RequestBody FormulaDTO formulaDTO) {
        FormulaResponse formulaResponse = formulaService.createFormula(formulaDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/formulas/{formulaId}")
                .buildAndExpand(formulaResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "Formula created successfully"));
    }

    @PutMapping(value = "/addSubType", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> addSubType(@RequestParam("medicineSubTypeId") Long medicineSubTypeId,
                                           @RequestParam("formulaId") Long formulaId) {
        formulaService.addSubType(medicineSubTypeId, formulaId);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/removeSubType", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> removeSubType(@RequestParam("medicineSubTypeId") Long medicineSubTypeId,
                                              @RequestParam("formulaId") Long formulaId) {
        formulaService.removeSubType(medicineSubTypeId, formulaId);

        return ResponseEntity.ok().build();
    }


}
