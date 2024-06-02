package com.liro.medicines.model.dbentities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "formulas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Formula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "baseFormula")
    private Set<Medicine> baseFormulaOf;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "medicines_medicine_sub_types",
            joinColumns = @JoinColumn(name = "medicine_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_sub_type_id", referencedColumnName = "id")
    )
    private Set<MedicineSubType> medicineSubTypes = new HashSet<>();

}
