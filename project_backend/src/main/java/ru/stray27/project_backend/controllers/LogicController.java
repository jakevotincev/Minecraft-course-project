package ru.stray27.project_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.project_backend.repositories.FoodRepository;
import ru.stray27.project_backend.repositories.SettlementRepository;

@RestController
@RequestMapping("/logic/")
@CrossOrigin(origins = "*")
public class LogicController {
    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private SettlementRepository settlementRepository;

    @GetMapping("eatFood")
    private ResponseEntity<?> eatFood(@RequestParam(name = "settlementId") Integer sId) {
        try {
            foodRepository.eatFoodProcedure(sId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("increasePopulation")
    private ResponseEntity<?> increasePopulation(@RequestParam(name = "settlementId") Integer sId) {
        try {
            settlementRepository.increasePopulation(sId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
