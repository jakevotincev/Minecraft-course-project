package ru.stray27.project_backend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Enchantment {
    @Id
    private Long id;
    private String name;
    private Double strengthBonus;
    private Double damageBonus;
    @ManyToMany(mappedBy = "enchantments")
    private Set<Weapon> weapons = new HashSet<>();
}
