package com.uralhalil.zapu.repository;

import com.uralhalil.zapu.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
    Category findByName(String name);
}
