package ru.stray27.project_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EnchantmentDto {
    private Integer id;
    private String name;
    private Double strengthBonus;
    private Double damageBonus;
    private Iterable<Integer> weaponsId;
}
