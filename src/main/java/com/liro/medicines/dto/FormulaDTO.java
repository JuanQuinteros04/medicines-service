package com.liro.medicines.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class FormulaDTO {

    private String name;

    private String formalName;

    private Integer approxDurationInMinutes;

    private Integer minRecommendedAge;

    private Boolean onlyVetUse;

    private Boolean needPrescription;

    private String details;

    private Double quantity;

    private String quantityType;
}
