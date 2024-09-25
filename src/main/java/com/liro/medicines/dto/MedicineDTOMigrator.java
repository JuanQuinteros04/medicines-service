package com.liro.medicines.dto;

import com.liro.medicines.model.enums.AnimalType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class MedicineDTOMigrator {

    private String commercialName;
    private String formalName;
    private Boolean onlyVetUse;
    private Boolean needPrescription;
    private String conservation;
    private String dosesUnity;
    private AnimalType animalType;

    private String brandName;
    private Long medicineTypeId;
    private List<String> medicineGroups;
    private String presentationName;
}
