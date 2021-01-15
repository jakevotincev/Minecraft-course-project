package ru.stray27.project_backend.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.project_backend.dto.WeaponDto;
import ru.stray27.project_backend.entities.Weapon;
import ru.stray27.project_backend.repositories.EnchantmentRepository;
import ru.stray27.project_backend.repositories.PeopleRepository;
import ru.stray27.project_backend.repositories.WeaponRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/weapon/")
@CrossOrigin(origins = "*")
public class WeaponController {
    @Autowired
    private WeaponRepository weaponRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private EnchantmentRepository enchantmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("readAll")
    private ResponseEntity<?> readAll() {
        try {
            Iterable<Weapon> elements = weaponRepository.findAll();
            List<WeaponDto> dto = StreamSupport.stream(elements.spliterator(), false).map(this::convertToDto).collect(Collectors.toList());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("read")
    private ResponseEntity<?> read(@RequestParam(name = "id") Integer id) {
        try {
            Weapon weapon = weaponRepository.findById(id).orElse(null);
            return new ResponseEntity<>(convertToDto(weapon), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("create")
    private ResponseEntity<?> create(@RequestBody WeaponDto weaponDto) {
        try {
            Weapon weapon = convertToEntity(weaponDto);
            weaponRepository.save(weapon);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("delete")
    private ResponseEntity<?> delete(@RequestBody WeaponDto weaponDto) {
        try {
            weaponRepository.deleteById(weaponDto.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private WeaponDto convertToDto(Weapon weapon) {
        return modelMapper.map(weapon, WeaponDto.class);
    }

    private Weapon convertToEntity(WeaponDto dto) {
        Weapon weapon = modelMapper.map(dto, Weapon.class);
        for (Integer eId : dto.getEnchantmentsId()) {
            weapon.getEnchantments().add(enchantmentRepository.findById(eId).orElse(null));
        }
        weapon.setOwner(peopleRepository.findById(dto.getOwnerId()).orElse(null));
        return weapon;
    }
}
