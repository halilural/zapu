package com.uralhalil.zapu.service;

import com.uralhalil.zapu.model.City;
import com.uralhalil.zapu.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;


    public void cityInit() {
        create("Antalya");
        create("İstanbul");
        create("Ankara");
    }

    public Boolean create(String name) {
        City existingCity = read(name);
        if (existingCity != null)
            return false;
        City city = new City(UUID.randomUUID().toString(), name);
        cityRepository.save(city);
        return true;
    }

    public City read(String name) {
        return cityRepository.findByName(name);
    }

    public List<City> readAll() {
        return cityRepository.findAll();
    }

    public Boolean delete(String name) {
        City existingCity = cityRepository.findByName(name);
        if (existingCity == null) {
            return false;
        }
        cityRepository.delete(existingCity);
        return true;
    }

}
