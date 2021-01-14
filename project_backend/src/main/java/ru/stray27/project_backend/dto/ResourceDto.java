package ru.stray27.project_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResourceDto {
    private Long id;
    private Integer amount;
    private Long settlementId;
}
