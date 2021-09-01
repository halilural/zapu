package com.uralhalil.zapu.repository;

import com.uralhalil.zapu.model.City;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CityRepository extends MongoRepository<City, String> {
    City findByName(String name);
}
