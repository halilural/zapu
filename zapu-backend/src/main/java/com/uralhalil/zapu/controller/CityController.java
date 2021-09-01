package com.uralhalil.zapu.controller;

import com.uralhalil.zapu.model.City;
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
    public City getList(@PathVariable("name") String name) {
        return service.read(name);
    }

    @PostMapping
    public Boolean create(@Valid @RequestBody String name) {
        return service.create(name);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable("name") String name) {
        service.delete(name);
        return ResponseEntity.ok().build();
    }

}
