package com.uralhalil.zapu.service;

import com.uralhalil.zapu.exception.NotFoundException;
import com.uralhalil.zapu.model.City;
import com.uralhalil.zapu.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;
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

    public City create(City city) {
        if (StringUtils.isEmpty(city.getName()))
            return null;
        City existingCity = null;
        try {
            existingCity = read(city.getName());
        } catch (NotFoundException exception) {

        }
        if (existingCity != null) {
            return null;
        }
        return cityRepository.save(city);
    }

    public City create(String name) {
        if (StringUtils.isEmpty(name))
            return null;
        City existingCity = null;
        try {
            existingCity = read(name);
        } catch (NotFoundException exception) {

        }
        if (existingCity != null)
            return null;
        return cityRepository.save(new City(UUID.randomUUID().toString(), name));
    }

    public City read(String name) throws NotFoundException {
        if (StringUtils.isEmpty(name))
            throw new NotFoundException();
        Optional<City> optionalCity = cityRepository.findByName(name);
        if (optionalCity == null || !optionalCity.isPresent())
            throw new NotFoundException(String.format("City with Name %s does not exist.", name));
        return optionalCity.get();
    }

    public List<City> readAll() {
        return cityRepository.findAll();
    }

    public Boolean delete(String name) throws NotFoundException {
        if (StringUtils.isEmpty(name))
            return false;
        City existingCity = read(name);
        if (existingCity == null) {
            return false;
        }
        cityRepository.delete(existingCity);
        return true;
    }

}
