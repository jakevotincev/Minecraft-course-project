package ru.stray27.project_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class WorldDto {
    private Integer id;
    private String name;
    private Iterable<Integer> settlementsId;
}
