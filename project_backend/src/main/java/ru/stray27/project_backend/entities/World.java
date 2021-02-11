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
public class World {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "world_gen")
    @SequenceGenerator(name = "world_gen", sequenceName = "world_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "world", fetch = FetchType.LAZY)
    private Set<Settlement> settlements = new HashSet<>();
}
