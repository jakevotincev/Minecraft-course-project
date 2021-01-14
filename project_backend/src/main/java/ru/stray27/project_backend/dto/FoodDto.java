package ru.stray27.project_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FoodDto {
    private Long id;
    private String name;
    private Double hpRegen;
    private Double satiety;
    private Integer amount;
    private Long settlementId;
}
