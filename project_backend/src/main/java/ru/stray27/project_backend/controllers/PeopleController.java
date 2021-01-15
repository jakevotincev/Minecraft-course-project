package ru.stray27.project_backend.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.project_backend.dto.PeopleDto;
import ru.stray27.project_backend.entities.People;
import ru.stray27.project_backend.repositories.BattleRepository;
import ru.stray27.project_backend.repositories.PeopleRepository;
import ru.stray27.project_backend.repositories.SettlementRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/people/")
@CrossOrigin(origins = "*")
public class PeopleController {
    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private SettlementRepository settlementRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("readAll")
    private ResponseEntity<?> readAll() {
        try {
            Iterable<People> elements = peopleRepository.findAll();
            List<PeopleDto> dto = StreamSupport.stream(elements.spliterator(), false).map(this::convertToDto).collect(Collectors.toList());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("read")
    private ResponseEntity<?> read(@RequestParam(name = "id") Integer id) {
        try {
            People people = peopleRepository.findById(id).orElse(null);
            return new ResponseEntity<>(convertToDto(people), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("create")
    private ResponseEntity<?> create(@RequestBody PeopleDto dto) {
        try {
            People people = convertToEntity(dto);
            peopleRepository.save(people);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("delete")
    private ResponseEntity<?> delete(@RequestBody PeopleDto dto) {
        try {
            peopleRepository.deleteById(dto.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private People convertToEntity(PeopleDto dto) {
        People people = modelMapper.map(dto, People.class);
        people.setSettlement(settlementRepository.findById(dto.getSettlementId()).orElse(null));
        for (Integer bId : dto.getBattlesId()) {
            people.getBattles().add(battleRepository.findById(bId).orElse(null));
        }
        return people;
    }

    private PeopleDto convertToDto(People people) {
        return modelMapper.map(people, PeopleDto.class);
    }
}
