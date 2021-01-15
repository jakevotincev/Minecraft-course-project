package ru.stray27.project_backend.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.project_backend.dto.ResourceDto;
import ru.stray27.project_backend.entities.Resource;
import ru.stray27.project_backend.repositories.ResourceRepository;
import ru.stray27.project_backend.repositories.SettlementRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("resource")
@CrossOrigin(origins = "*")
public class ResourceController {
    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private SettlementRepository settlementRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("readAll")
    private ResponseEntity<?> readAll() {
        try {
            Iterable<Resource> elements = resourceRepository.findAll();
            List<ResourceDto> dto = StreamSupport.stream(elements.spliterator(), false).map(this::convertToDto).collect(Collectors.toList());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("read")
    private ResponseEntity<?> read(@RequestParam(name = "id") Integer id) {
        try {
            Resource resource = resourceRepository.findById(id).orElse(null);
            return new ResponseEntity<>(convertToDto(resource), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("create")
    private ResponseEntity<?> create(@RequestBody ResourceDto dto) {
        try {
            Resource resource = convertToEntity(dto);
            resourceRepository.save(resource);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("delete")
    private ResponseEntity<?> delete(@RequestBody ResourceDto dto) {
        try {
            resourceRepository.deleteById(dto.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private Resource convertToEntity(ResourceDto dto) {
        Resource resource = modelMapper.map(dto, Resource.class);
        resource.setSettlement(settlementRepository.findById(dto.getSettlementId()).orElse(null));
        return resource;
    }

    private ResourceDto convertToDto(Resource resource) {
        return modelMapper.map(resource, ResourceDto.class);
    }
}
