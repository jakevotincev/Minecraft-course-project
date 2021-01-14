package ru.stray27.project_backend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class World {
    private Long id;
    private String name;
    @OneToMany(mappedBy = "world", fetch = FetchType.LAZY)
    private Set<Settlement> settlements = new HashSet<>();
}
