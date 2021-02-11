package ru.stray27.project_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BattleDto {
    private Integer id;
    private Integer peopleId;
    private Integer monsterId;
    private Double damageToPerson;
    private Double damageToMonster;
}
