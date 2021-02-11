package ru.stray27.project_backend.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.project_backend.dto.BattleDto;
import ru.stray27.project_backend.entities.Battle;
import ru.stray27.project_backend.repositories.BattleRepository;
import ru.stray27.project_backend.repositories.MonsterRepository;
import ru.stray27.project_backend.repositories.PeopleRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/battle/")
@CrossOrigin(origins = "*")
public class BattleController {
    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private MonsterRepository monsterRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("readAll")
    private ResponseEntity<?> readAll() {
        try {
            Iterable<Battle> elements = battleRepository.findAll();
            List<BattleDto> dtos = StreamSupport.stream(elements.spliterator(), false).map(this::convertToDto).collect(Collectors.toList());
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("read")
    private ResponseEntity<?> read(@RequestParam(name = "id") Integer id) {
        try {
            Battle battle = battleRepository.findById(id).orElse(null);
            return new ResponseEntity<>(convertToDto(battle), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("create")
    private ResponseEntity<?> create(@RequestBody BattleDto battleDto) {
        try {
            Battle battle = convertToEntity(battleDto);
            if (battle.getMonster() == null || battle.getPeople() == null) {
                throw new Exception();
            }
            battleRepository.save(battle);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("delete")
    private ResponseEntity<?> delete(@RequestBody BattleDto battleDto) {
        try {
            battleRepository.deleteById(battleDto.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private BattleDto convertToDto(Battle battle) {
        return modelMapper.map(battle, BattleDto.class);
    }

    private Battle convertToEntity(BattleDto battleDto) {
        Battle battle = modelMapper.map(battleDto, Battle.class);
        battle.setMonster(monsterRepository.findById(battleDto.getMonsterId()).orElse(null));
        battle.setPeople(peopleRepository.findById(battleDto.getPeopleId()).orElse(null));
        return battle;
    }
}
