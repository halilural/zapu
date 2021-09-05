package com.uralhalil.zapu.controller;

import com.uralhalil.zapu.exception.NotFoundException;
import com.uralhalil.zapu.model.Property;
import com.uralhalil.zapu.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    PropertyService service;

    @GetMapping
    public List<Property> getList() {
        return service.readAll();
    }

    @GetMapping("/{id}")
    public Property getSingle(@PathVariable("id") String id) {
        Property property = null;
        try {
            property = service.read(id);
        } catch (NotFoundException exception) {
        }
        return property;
    }

    @PutMapping("/{id}")
    public Property updateSingle(@PathVariable("id") String id, @Valid @RequestBody Property property) throws NotFoundException {
        return service.update(id, property);
    }

    @PostMapping
    public Property create(@Valid @RequestBody Property property) {
        return service.create(property);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) throws NotFoundException {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
