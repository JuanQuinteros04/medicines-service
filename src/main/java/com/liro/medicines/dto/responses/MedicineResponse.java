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

    private Long brandId;
    private Long medicineTypeId;
    private Long medicineGroupId;
    private Long presentationId;

    private List<Long> components;
}
