package ru.stray27.project_backend.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.project_backend.dto.EnchantmentDto;
import ru.stray27.project_backend.entities.Enchantment;
import ru.stray27.project_backend.repositories.EnchantmentRepository;
import ru.stray27.project_backend.repositories.WeaponRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/enchantment/")
@CrossOrigin(origins = "*")
public class EnchantmentController {
    @Autowired
    private EnchantmentRepository enchantmentRepository;

    @Autowired
    private WeaponRepository weaponRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("readAll")
    private ResponseEntity<?> readAll() {
        try {
            Iterable<Enchantment> elements = enchantmentRepository.findAll();
            List<EnchantmentDto> dto = StreamSupport.stream(elements.spliterator(), false).map(this::convertToDto).collect(Collectors.toList());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("read")
    private ResponseEntity<?> read(@RequestParam(name = "id") Integer id) {
        try {
            Enchantment enchantment = enchantmentRepository.findById(id).orElse(null);
            return new ResponseEntity<>(convertToDto(enchantment), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("create")
    private ResponseEntity<?> create(@RequestBody EnchantmentDto enchantmentDto) {
        try {
            Enchantment enchantment = convertToEntity(enchantmentDto);
            enchantmentRepository.save(enchantment);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("delete")
    private ResponseEntity<?> delete(@RequestBody EnchantmentDto enchantmentDto) {
        try {
            enchantmentRepository.deleteById(enchantmentDto.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private EnchantmentDto convertToDto(Enchantment enchantment) {
        return modelMapper.map(enchantment, EnchantmentDto.class);
    }

    private Enchantment convertToEntity(EnchantmentDto dto) {
        Enchantment enchantment = modelMapper.map(dto, Enchantment.class);
        for (Integer wId : dto.getWeaponsId()) {
            enchantment.getWeapons().add(weaponRepository.findById(wId).orElse(null));
        }
        return enchantment;
    }
}
