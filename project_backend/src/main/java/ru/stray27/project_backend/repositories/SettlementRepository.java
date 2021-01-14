package ru.stray27.project_backend.repositories;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import ru.stray27.project_backend.entities.Settlement;

public interface SettlementRepository extends CrudRepository<Settlement, Long> {
    @Procedure(name = "increasePopulation")
    void increasePopulation(Long id);
}
