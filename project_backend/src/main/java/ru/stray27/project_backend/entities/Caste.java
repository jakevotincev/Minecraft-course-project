package ru.stray27.project_backend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Caste {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "caste_gen")
    @SequenceGenerator(name = "caste_gen", sequenceName = "caste_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    @Column(name = "hunger_ratio")
    private Double hungerRatio;
    @OneToMany(mappedBy = "caste", fetch = FetchType.LAZY)
    Set<Settlement> settlements = new HashSet<>();
    @OneToMany(mappedBy = "caste", fetch = FetchType.LAZY)
    Set<People> people = new HashSet<>();
}
