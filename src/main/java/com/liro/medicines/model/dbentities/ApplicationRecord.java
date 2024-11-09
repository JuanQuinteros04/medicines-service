package com.liro.medicines.model.dbentities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@Table(name = "application_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long animalId;
    private Long  vetProfileId;
    private Long vetClinicId;
    private boolean valid;
    private String details;

//    @Min(value = 0, message = "The minimum value is 0!")
    private Float quantity;

    private String lote;
    private String controlNumber;
    private String serieNumber;
    private LocalDate applicationDate;
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;
}
