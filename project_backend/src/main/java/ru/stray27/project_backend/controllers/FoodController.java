package ru.stray27.project_backend.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.project_backend.dto.FoodDto;
import ru.stray27.project_backend.entities.Food;
import ru.stray27.project_backend.repositories.FoodRepository;
import ru.stray27.project_backend.repositories.SettlementRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/food/")
@CrossOrigin(origins = "*")
public class FoodController {
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private SettlementRepository settlementRepository;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("readAll")
    private ResponseEntity<?> readAll() {
        try {
            Iterable<Food> elements = foodRepository.findAll();
            List<FoodDto> dto = StreamSupport.stream(elements.spliterator(), false).map(this::convertToDto).collect(Collectors.toList());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("read")
    private ResponseEntity<?> read(@RequestParam(name = "id") Integer id) {
        try {
            Food food = foodRepository.findById(id).orElse(null);
            return new ResponseEntity<>(convertToDto(food), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("create")
    private ResponseEntity<?> create(@RequestBody FoodDto dto) {
        try {
            Food food = convertToEntity(dto);
            foodRepository.save(food);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("delete")
    private ResponseEntity<?> delete(@RequestBody FoodDto dto) {
        try {
            foodRepository.deleteById(dto.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private Food convertToEntity(FoodDto dto) {
        Food food = modelMapper.map(dto, Food.class);
        food.setSettlement(settlementRepository.findById(dto.getSettlementId()).orElse(null));
        return food;
    }

    private FoodDto convertToDto(Food food) {
        return modelMapper.map(food, FoodDto.class);
    }
}
