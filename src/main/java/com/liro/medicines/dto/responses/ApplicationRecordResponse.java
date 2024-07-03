package com.liro.medicines.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class ApplicationRecordResponse {

    private Long id;
    private Long animalId;
    private Long  vetProfileId;
    private boolean valid;
    private String details;
    private Long quantity;
    private String lote;
    private LocalDate applicationDate;
    private LocalDate endDate;

    private Boolean isApplied;

    private Long medicineId;
}
