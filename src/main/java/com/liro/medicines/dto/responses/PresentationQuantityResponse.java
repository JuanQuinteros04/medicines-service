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
public class PresentationQuantityResponse {

    private Long id;

    private Double quantity;

    private String quantityType;

    private Double quantityPerDose;

    private String quantityTypePerDose;

    private Double dosesPerWeight;

    private String weightTypePerDose;

    private String details;

    private Long presentationId;
}
