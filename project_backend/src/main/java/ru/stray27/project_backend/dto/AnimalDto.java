package ru.stray27.project_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AnimalDto {
    private Long id;
    private String name;
    private Double health;
    private Double damage;
    private Boolean isPregnant;
    private Integer gestationalAge;
    private Long ownerId;
}
