package ru.stray27.project_backend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Caste {
    private Long id;
    private String name;
    @Column(name = "hunger_ratio")
    private Double hungerRatio;
    @OneToMany(mappedBy = "caste", fetch = FetchType.LAZY)
    Set<Settlement> settlements = new HashSet<>();
    @OneToMany(mappedBy = "caste", fetch = FetchType.LAZY)
    Set<People> people = new HashSet<>();
}
