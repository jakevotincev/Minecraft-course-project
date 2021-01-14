package ru.stray27.project_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CasteDto {
    private Long id;
    private String name;
    private Double hungerRatio;
    Iterable<Long> settlementsId;
    Iterable<Long> peopleId;
}
