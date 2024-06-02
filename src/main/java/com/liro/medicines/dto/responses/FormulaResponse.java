package com.liro.medicines.dto.responses;

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
public class FormulaResponse {

    private Long id;
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
