package ru.stray27.project_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.stray27.project_backend.entities.Enchantment;
import ru.stray27.project_backend.entities.People;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class WeaponDto {
    private Long id;
    private String name;
    private Double strength;
    private Double damage;
    private Long ownerId;
    private Iterable<Long> enchantmentsId;
}
