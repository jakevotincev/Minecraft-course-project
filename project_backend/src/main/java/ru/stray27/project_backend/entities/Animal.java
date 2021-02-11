package ru.stray27.project_backend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_gen")
    @SequenceGenerator(name = "animal_gen", sequenceName = "animal_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private Double health;
    private Double damage;
    private Boolean isPregnant;
    private Integer gestationalAge;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private People owner;
}
