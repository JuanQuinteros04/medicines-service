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
public class MedicineResponse {

    private Long id;

    private String name;

    private String formalName;

    private Integer approxDurationInMinutes;

    private Integer minRecommendedAge;

    private Boolean onlyVetUse;

    private Boolean needPrescription;

    private String conservation;

    private Long brandId;

    private Long baseFormulaId;
}
