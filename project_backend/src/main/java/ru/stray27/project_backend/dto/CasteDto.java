package ru.stray27.project_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CasteDto {
    private Integer id;
    private String name;
    private Double hungerRatio;
    Iterable<Integer> settlementsId;
    Iterable<Integer> peopleId;
}
