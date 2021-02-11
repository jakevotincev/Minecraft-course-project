package ru.stray27.project_backend.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.stray27.project_backend.entities.World;

public interface WorldRepository extends CrudRepository<World, Integer> {
}
