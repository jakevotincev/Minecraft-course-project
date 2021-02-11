package ru.stray27.project_backend.repositories;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import ru.stray27.project_backend.entities.Food;

public interface FoodRepository extends CrudRepository<Food, Integer> {
    @Procedure(name = "eatFood")
    void eatFoodProcedure(Integer settlementId);
}
