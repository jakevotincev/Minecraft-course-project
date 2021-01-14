package ru.stray27.project_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SettlementDto {
    private Long id;
    private Integer population;
    private Integer positionX;
    private Integer positionY;
    private Long worldId;
    private Long casteId;
    private Iterable<Long> foodId;
    private Iterable<Long> resourcesId;
    private Iterable<Long> peopleId;
}
