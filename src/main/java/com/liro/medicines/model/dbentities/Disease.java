package com.liro.medicines.model.dbentities;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "disease")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
}
