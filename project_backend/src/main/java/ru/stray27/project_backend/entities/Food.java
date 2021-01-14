package ru.stray27.project_backend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Food {
    private Long id;
    private String name;
    @Column(name = "hp_regen")
    private Double hpRegen;
    private Double satiety;
    private Integer amount;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Settlement settlement;
}
