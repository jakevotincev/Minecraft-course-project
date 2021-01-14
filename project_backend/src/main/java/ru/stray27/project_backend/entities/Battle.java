package ru.stray27.project_backend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Battle {
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private People people;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Monster monster;
    private Double damageToPerson;
    private Double damageToMonster;
}
