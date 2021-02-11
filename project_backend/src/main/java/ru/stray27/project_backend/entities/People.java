package ru.stray27.project_backend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "people_gen")
    @SequenceGenerator(name = "people_gen", sequenceName = "people_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private Double health;
    private Double strength;
    private Double money;
    private Boolean isPregnant;
    private Integer gestationalAge;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Settlement settlement;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Caste caste;
    @OneToMany(mappedBy = "people")
    private Set<Battle> battles = new HashSet<>();
}
