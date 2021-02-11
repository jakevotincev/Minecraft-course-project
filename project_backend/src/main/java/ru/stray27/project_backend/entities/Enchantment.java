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
public class Enchantment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enchantment_gen")
    @SequenceGenerator(name = "enchantment_gen", sequenceName = "enchantment_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private Double strengthBonus;
    private Double damageBonus;
    @ManyToMany(mappedBy = "enchantments")
    private Set<Weapon> weapons = new HashSet<>();
}
