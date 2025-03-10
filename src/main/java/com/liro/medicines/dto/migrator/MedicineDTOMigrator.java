package com.liro.medicines.dto.migrator;

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
    private String medicineType;
    private List<String> medicineGroups;
    private List<String> components;

    private String presentationName;
}
