package com.liro.medicines.model.dbentities;

import com.liro.medicines.model.enums.AnimalType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "medicines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String commercialName;
    private String formalName;
    private Boolean onlyVetUse;
    private Boolean needPrescription;
    private String conservation;
    private Long content;
    private String dosesUnity;
    private AnimalType animalType;


    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "presentation_id")
    private Presentation presentation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_type_id")
    private MedicineType medicineType;

    @ManyToMany
    @JoinTable(
            name = "medicines_groups",
            joinColumns = @JoinColumn(name = "medicine_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id")
    )
    private List<MedicineGroup> medicineGroups;

    @ManyToMany
    @JoinTable(
            name = "medicines_components",
            joinColumns = @JoinColumn(name = "medicine_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "component_id", referencedColumnName = "id")
    )
    private List<Component> components = new ArrayList<>();

}
