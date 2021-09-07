package com.uralhalil.zapu.service;

import com.uralhalil.zapu.exception.NotFoundException;
import com.uralhalil.zapu.model.entity.Category;
import com.uralhalil.zapu.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;
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

    public Category create(Category category) {
        if (StringUtils.isEmpty(category.getName()))
            return null;
        Category existingCategory = null;
        try {
            existingCategory = read(category.getName());
        } catch (NotFoundException exception) {

        }
        if (existingCategory != null) {
            return null;
        }
        return categoryRepository.save(category);
    }

    public Category create(String name) {
        if (StringUtils.isEmpty(name))
            return null;
        Category existingCategory = null;
        try {
            existingCategory = read(name);
        } catch (NotFoundException exception) {

        }
        if (existingCategory != null)
            return null;
        return categoryRepository.save(new Category(UUID.randomUUID().toString(), name));
    }

    public Category read(String name) throws NotFoundException {
        if (StringUtils.isEmpty(name))
            throw new NotFoundException();
        Optional<Category> optionalCategory = categoryRepository.findByName(name);
        if (optionalCategory == null || !optionalCategory.isPresent())
            throw new NotFoundException("Category", "Name", name);
        return optionalCategory.get();
    }

    public List<Category> readAll() {
        return categoryRepository.findAll();
    }

    public Boolean delete(String name) throws NotFoundException {
        if (StringUtils.isEmpty(name))
            return false;
        categoryRepository.delete(read(name));
        return true;
    }

    public void deleteAll() {
        categoryRepository.deleteAll();
    }

}
