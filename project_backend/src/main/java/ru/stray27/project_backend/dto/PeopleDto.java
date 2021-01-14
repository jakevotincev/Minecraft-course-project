package ru.stray27.project_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PeopleDto {
    private Long id;
    private String name;
    private Double health;
    private Double strength;
    private Double money;
    private Boolean isPregnant;
    private Integer gestationalAge;
    private Long settlementId;
    private Long casteId;
    private Iterable<Long> battlesId;
}
