package com.liro.medicines.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.liro.medicines.model.enums.AnimalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class MedicineDTO {

    private String commercialName;
    private String formalName;
    private Boolean onlyVetUse;
    private Boolean needPrescription;
    private String conservation;
    private Long content;
    private String dosesUnity;
    private AnimalType animalType;


    private Long brandId;
    private Long medicineTypeId;
    private Long medicineGroupId;
    private Long presentationId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Long> components;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    public void setComponents(List<Long> components) {
        this.components = components;
    }
}
