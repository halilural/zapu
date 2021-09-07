package com.uralhalil.zapu.controller;

import com.uralhalil.zapu.exception.NotFoundException;
import com.uralhalil.zapu.model.entity.City;
import com.uralhalil.zapu.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    CityService service;

    @GetMapping
    public List<City> getList() {
        return service.readAll();
    }

    @GetMapping("/{name}")
    public City getSingle(@PathVariable("name") String name) {
        City city = null;
        try {
            city = service.read(name);
        } catch (NotFoundException exception) {
        }
        return city;
    }

    @PostMapping
    public City create(@Valid @RequestBody City city) {
        return service.create(city);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable("name") String name) throws NotFoundException {
        service.delete(name);
        return ResponseEntity.ok().build();
    }


}
