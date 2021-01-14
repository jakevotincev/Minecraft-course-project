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
public class Settlement {
    private Long id;
    private Integer population;
    private Integer positionX;
    private Integer positionY;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "world_id")
    private World world;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "caste_id")
    private Caste caste;
    @OneToMany(mappedBy = "settlement")
    private Set<Food> food = new HashSet<>();
    @OneToMany(mappedBy = "settlement")
    private Set<Resource> resources = new HashSet<>();
    @OneToMany(mappedBy = "settlement")
    private Set<People> people = new HashSet<>();
}
