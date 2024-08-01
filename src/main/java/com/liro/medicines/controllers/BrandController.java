package com.liro.medicines.controllers;
import com.liro.medicines.dto.ApiResponse;
import com.liro.medicines.dto.BrandDTO;
import com.liro.medicines.dto.responses.BrandResponse;
import com.liro.medicines.service.BrandService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;

@RestController
@RequestMapping("/brands")
public class BrandController {

        private final BrandService brandService;

        @Autowired
        public BrandController(BrandService brandService) {
            this.brandService = brandService;
        }

    @GetMapping(value = "/{brandId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BrandResponse> getBrand(
            @PathVariable("brandId") Long brandId) {
        return ResponseEntity.ok(brandService.findById(brandId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<BrandResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(brandService.findAll(pageable));
    }

    @ApiPageable
    @GetMapping(value = "/getByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<BrandResponse>> getByNameContaining(
            @RequestParam("nameContaining") String nameContaining, Pageable pageable) {
        return ResponseEntity.ok(brandService.findAllByNameContaining(nameContaining, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createBrand(@Valid @RequestBody BrandDTO brandDto) {
        BrandResponse brandResponse = brandService.createBrand(brandDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/brands/{brandId}")
                .buildAndExpand(brandResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "Brand created successfully"));
    }

    @Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    @interface ApiPageable {
    }
}
