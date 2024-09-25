package com.liro.medicines.model.dbentities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private String commercialName;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "brand")
    private Set<Medicine> medicines = new HashSet<>();

}
