package ru.stray27.project_backend.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.project_backend.dto.AnimalDto;
import ru.stray27.project_backend.entities.Animal;
import ru.stray27.project_backend.repositories.AnimalRepository;
import ru.stray27.project_backend.repositories.PeopleRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController("/animal/")
@CrossOrigin(origins = "*")
public class AnimalController {
    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("readAll")
    private ResponseEntity<?> readAll() {
        try {
            Iterable<Animal> elements = animalRepository.findAll();
            List<AnimalDto> result = StreamSupport.stream(elements.spliterator(), false).map(this::convertToDto).collect(Collectors.toList());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("read")
    private ResponseEntity<?> read(@RequestAttribute(name = "id") Long id) {
        try {
            Animal animal = animalRepository.findById(id).orElse(null);
            return new ResponseEntity<>(convertToDto(animal), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("create")
    private ResponseEntity<?> create(@RequestBody AnimalDto animalDto) {
        try {
            Animal animal = convertToEntity(animalDto);
            if (animal.getOwner() == null) {
                throw new Exception();
            }
            animalRepository.save(animal);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("delete")
    private ResponseEntity<?> delete(@RequestBody AnimalDto animalDto) {
        try {
            animalRepository.deleteById(animalDto.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private AnimalDto convertToDto(Animal animal) {
        AnimalDto dto = modelMapper.map(animal, AnimalDto.class);
        dto.setOwnerId(animal.getOwner().getId());
        return dto;
    }

    private Animal convertToEntity(AnimalDto animalDto) {
        Animal animal = modelMapper.map(animalDto, Animal.class);
        animal.setOwner(peopleRepository.findById(animalDto.getOwnerId()).orElse(null));
        return animal;
    }
}
