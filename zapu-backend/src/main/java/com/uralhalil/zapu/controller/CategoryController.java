package com.uralhalil.zapu.controller;

import com.uralhalil.zapu.exception.NotFoundException;
import com.uralhalil.zapu.model.Category;
import com.uralhalil.zapu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService service;

    @GetMapping
    public List<Category> getList() {
        return service.readAll();
    }

    @GetMapping("/{name}")
    public Category getSingle(@PathVariable("name") String name) {
        Category category = null;
        try {
            category = service.read(name);
        } catch (NotFoundException exception) {
        }
        return category;
    }

    @PostMapping
    public Category create(@Valid @RequestBody Category category) {
        return service.create(category);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable("name") String name) throws NotFoundException {
        service.delete(name);
        return ResponseEntity.ok().build();
    }

}
