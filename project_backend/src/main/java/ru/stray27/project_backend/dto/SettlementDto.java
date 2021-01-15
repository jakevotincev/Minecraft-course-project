package ru.stray27.project_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SettlementDto {
    private Integer id;
    private Integer population;
    private Integer positionX;
    private Integer positionY;
    private Integer worldId;
    private Integer casteId;
    private Iterable<Integer> foodId;
    private Iterable<Integer> resourcesId;
    private Iterable<Integer> peopleId;
}
