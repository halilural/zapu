package com.uralhalil.zapu.repository;

import com.uralhalil.zapu.model.entity.City;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CityRepository extends MongoRepository<City, String> {
    Optional<City> findByName(String name);
}
