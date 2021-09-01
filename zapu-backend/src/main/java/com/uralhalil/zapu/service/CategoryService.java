package com.uralhalil.zapu.service;

import com.uralhalil.zapu.model.Category;
import com.uralhalil.zapu.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public void categoryInit() {
        create("konut");
        create("ticari");
        create("arsa");
    }

    public Boolean create(String name) {
        Category existingCategory = read(name);
        if (existingCategory != null)
            return false;
        Category category = new Category(UUID.randomUUID().toString(), name);
        categoryRepository.save(category);
        return true;
    }

    public Category read(String name) {
        return categoryRepository.findByName(name);
    }

    public List<Category> readAll() {
        return categoryRepository.findAll();
    }

    public Boolean delete(String name) {
        Category existingCategory = categoryRepository.findByName(name);
        if (existingCategory == null) {
            return false;
        }
        categoryRepository.delete(existingCategory);
        return true;
    }

}
