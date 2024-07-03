package com.liro.medicines.model.dbentities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "component")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "disease_id")
    private Disease disease;
}
