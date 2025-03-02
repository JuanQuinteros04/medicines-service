package com.liro.medicines.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liro.medicines.repositories.MedicineRepository;
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
    private String controlNumber;
    private String serieNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate applicationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate endDate;

    private MedicineResponse medicine;
}
