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
public class Weapon {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weapon_gen")
    @SequenceGenerator(name = "weapon_gen", sequenceName = "weapon_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private Double strength;
    private Double damage;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private People owner;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "weapon_enchantment",
            joinColumns = @JoinColumn(name = "weapon_id"),
            inverseJoinColumns = @JoinColumn(name = "enchantment_id"))
    private Set<Enchantment> enchantments = new HashSet();
}
