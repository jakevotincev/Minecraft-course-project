package ru.stray27.project_backend.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.project_backend.dto.BattleDto;
import ru.stray27.project_backend.dto.WorldDto;
import ru.stray27.project_backend.entities.World;
import ru.stray27.project_backend.repositories.SettlementRepository;
import ru.stray27.project_backend.repositories.WorldRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/world/")
@CrossOrigin(origins = "*")
public class WorldController {
    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private SettlementRepository settlementRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("readAll")
    private ResponseEntity<?> readAll() {
        try {
            Iterable<World> elements = worldRepository.findAll();
            List<WorldDto> dtos = StreamSupport.stream(elements.spliterator(), false).map(this::convertToDto).collect(Collectors.toList());
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("read")
    private ResponseEntity<?> read(@RequestParam(name = "id") Integer id) {
        try {
            World world = worldRepository.findById(id).orElse(null);
            return new ResponseEntity<>(convertToDto(world), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("create")
    private ResponseEntity<?> create(@RequestBody WorldDto worldDto) {
        try {
            World world = convertToEntity(worldDto);
            worldRepository.save(world);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("delete")
    private ResponseEntity<?> delete(@RequestBody WorldDto worldDto) {
        try {
            worldRepository.deleteById(worldDto.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private WorldDto convertToDto(World world) {
        return modelMapper.map(world, WorldDto.class);
    }

    private World convertToEntity(WorldDto dto) {
        World world = modelMapper.map(dto, World.class);
        for (Integer sId : dto.getSettlementsId()) {
            world.getSettlements().add(settlementRepository.findById(sId).orElse(null));
        }
        return world;
    }
}
