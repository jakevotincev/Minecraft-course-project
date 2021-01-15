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
public class Monster {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "monster_gen")
    @SequenceGenerator(name = "monster_gen", sequenceName = "monster_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private Double health;
    private Double damage;
    @OneToMany(mappedBy = "monster")
    private Set<Battle> battles = new HashSet<>();
}
