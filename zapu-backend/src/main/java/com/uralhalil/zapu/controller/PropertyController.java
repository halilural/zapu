package com.uralhalil.zapu.controller;

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
        return service.read(id);
    }

    @PutMapping("/{id}")
    public Property updateSingle(@PathVariable("id") String id, @Valid @RequestBody Property property) {
        return service.update(id, property);
    }

    @PostMapping
    public Boolean create(@Valid @RequestBody Property property) {
        return service.create(property);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
