package com.liro.medicines.dto.responses;

import com.liro.medicines.model.enums.AnimalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class MedicineResponse {

    private Long id;

    private String commercialName;
    private String formalName;
    private Boolean onlyVetUse;
    private Boolean needPrescription;
    private String conservation;
    private Long content;
    private String dosesUnity;
    private AnimalType animalType;

    private BrandResponse brand;
    private MedicineTypeResponse medicineType;
    private MedicineGroupResponse medicineGroup;
    private PresentationResponse presentation;

    private List<ComponentResponse> components;
}

