package ru.stray27.project_backend.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.project_backend.dto.CasteDto;
import ru.stray27.project_backend.entities.Caste;
import ru.stray27.project_backend.repositories.CasteRepository;
import ru.stray27.project_backend.repositories.PeopleRepository;
import ru.stray27.project_backend.repositories.SettlementRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/caste/")
@CrossOrigin(origins = "*")
public class CasteController {
    @Autowired
    private CasteRepository casteRepository;
    @Autowired
    private PeopleRepository peopleRepository;
    @Autowired
    private SettlementRepository settlementRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("readAll")
    private ResponseEntity<?> readAll() {
        try {
            Iterable<Caste> elements = casteRepository.findAll();
            List<CasteDto> dto = StreamSupport.stream(elements.spliterator(), false).map(this::convertToDto).collect(Collectors.toList());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("read")
    private ResponseEntity<?> read(@RequestParam(name = "id") Integer id) {
        try {
            Caste caste = casteRepository.findById(id).orElse(null);
            return new ResponseEntity<>(convertToDto(caste), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("create")
    private ResponseEntity<?> create(@RequestBody CasteDto casteDto) {
        try {
            Caste caste = convertToEntity(casteDto);
            casteRepository.save(caste);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("delete")
    private ResponseEntity<?> delete(@RequestBody CasteDto casteDto) {
        try {
            casteRepository.deleteById(casteDto.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private CasteDto convertToDto(Caste caste) {
        return modelMapper.map(caste, CasteDto.class);
    }

    private Caste convertToEntity(CasteDto dto) {
        Caste caste = modelMapper.map(dto, Caste.class);
        for (Integer pId : dto.getPeopleId()) {
            caste.getPeople().add(peopleRepository.findById(pId).orElse(null));
        }
        for (Integer sId : dto.getSettlementsId()) {
            caste.getSettlements().add(settlementRepository.findById(sId).orElse(null));
        }
        return caste;
    }


}
