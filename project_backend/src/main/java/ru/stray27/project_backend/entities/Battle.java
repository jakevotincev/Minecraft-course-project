package ru.stray27.project_backend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Battle {
    @Id
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private People people;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Monster monster;
    private Double damageToPerson;
    private Double damageToMonster;
}
