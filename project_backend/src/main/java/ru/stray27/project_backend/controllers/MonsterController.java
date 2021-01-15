package ru.stray27.project_backend.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.project_backend.dto.MonsterDto;
import ru.stray27.project_backend.entities.Monster;
import ru.stray27.project_backend.repositories.BattleRepository;
import ru.stray27.project_backend.repositories.MonsterRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/monster/")
@CrossOrigin(origins = "*")
public class MonsterController {
    @Autowired
    private MonsterRepository monsterRepository;

    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("readAll")
    private ResponseEntity<?> readAll() {
        try {
            Iterable<Monster> elements = monsterRepository.findAll();
            List<MonsterDto> dto = StreamSupport.stream(elements.spliterator(), false).map(this::convertToDto).collect(Collectors.toList());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("read")
    private ResponseEntity<?> read(@RequestParam(name = "id") Integer id) {
        try {
            Monster monster = monsterRepository.findById(id).orElse(null);
            return new ResponseEntity<>(convertToDto(monster), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("create")
    private ResponseEntity<?> create(@RequestBody MonsterDto monsterDto) {
        try {
            Monster monster = convertToEntity(monsterDto);
            monsterRepository.save(monster);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("delete")
    private ResponseEntity<?> delete(@RequestBody MonsterDto monsterDto) {
        try {
            monsterRepository.deleteById(monsterDto.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private MonsterDto convertToDto(Monster monster) {
        return modelMapper.map(monster, MonsterDto.class);
    }

    private Monster convertToEntity(MonsterDto dto) {
        Monster monster = modelMapper.map(dto, Monster.class);
        for (Integer bId : dto.getBattlesId()) {
            monster.getBattles().add(battleRepository.findById(bId).orElse(null));
        }
        return monster;
    }
}
