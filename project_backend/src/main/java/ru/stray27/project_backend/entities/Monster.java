package ru.stray27.project_backend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Monster {
    @Id
    private Long id;
    private String name;
    private Double health;
    private Double damage;
    @OneToMany(mappedBy = "monster")
    private Set<Battle> battles = new HashSet<>();
}
