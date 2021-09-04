package com.uralhalil.zapu.repository;

import com.uralhalil.zapu.model.Property;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PropertyRepository extends MongoRepository<Property, String> {
    Optional<Property> findByTitle(String title);
}
