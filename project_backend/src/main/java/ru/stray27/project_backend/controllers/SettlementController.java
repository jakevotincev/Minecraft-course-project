package ru.stray27.project_backend.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.project_backend.dto.SettlementDto;
import ru.stray27.project_backend.entities.Settlement;
import ru.stray27.project_backend.repositories.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/settlement/")
@CrossOrigin(origins = "*")
public class SettlementController {
    @Autowired
    private SettlementRepository settlementRepository;

    @Autowired
    private CasteRepository casteRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("readAll")
    private ResponseEntity<?> readAll() {
        try {
            Iterable<Settlement> elements = settlementRepository.findAll();
            List<SettlementDto> dto = StreamSupport.stream(elements.spliterator(), false).map(this::convertToDto).collect(Collectors.toList());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("read")
    private ResponseEntity<?> read(@RequestParam(name = "id") Integer id) {
        try {
            Settlement settlement = settlementRepository.findById(id).orElse(null);
            return new ResponseEntity<>(convertToDto(settlement), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("create")
    private ResponseEntity<?> create(@RequestBody SettlementDto dto) {
        try {
            Settlement settlement = convertToEntity(dto);
            settlementRepository.save(settlement);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("delete")
    private ResponseEntity<?> delete(@RequestBody SettlementDto dto) {
        try {
            settlementRepository.deleteById(dto.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private SettlementDto convertToDto(Settlement settlement) {
        return modelMapper.map(settlement, SettlementDto.class);
    }

    private Settlement convertToEntity(SettlementDto dto) {
        Settlement settlement = modelMapper.map(dto, Settlement.class);
        settlement.setCaste(casteRepository.findById(dto.getCasteId()).orElse(null));
        settlement.setWorld(worldRepository.findById(dto.getWorldId()).orElse(null));
        for (Integer fId : dto.getFoodId()) {
            settlement.getFood().add(foodRepository.findById(fId).orElse(null));
        }
        for (Integer pId : dto.getPeopleId()) {
            settlement.getPeople().add(peopleRepository.findById(pId).orElse(null));
        }
        for (Integer rId : dto.getResourcesId()) {
            settlement.getResources().add(resourceRepository.findById(rId).orElse(null));
        }
        return settlement;
    }
}
