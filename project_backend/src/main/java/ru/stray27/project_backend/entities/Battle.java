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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "battle_gen")
    @SequenceGenerator(name = "battle_gen", sequenceName = "battle_id_seq", allocationSize = 1)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private People people;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Monster monster;
    private Double damageToPerson;
    private Double damageToMonster;
}
