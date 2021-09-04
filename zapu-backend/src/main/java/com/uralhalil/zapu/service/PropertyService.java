package com.uralhalil.zapu.service;

import com.uralhalil.zapu.model.Property;
import com.uralhalil.zapu.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public Boolean create(Property property) {
        if (property == null)
            return false;
        property.setId(UUID.randomUUID().toString());
        propertyRepository.save(property);
        return true;
    }

    public Property read(String id) {
        if (StringUtils.isEmpty(id))
            return null;
        Optional<Property> optionalProperty = propertyRepository.findById(id);
        if (!optionalProperty.isPresent())
            return null;
        return optionalProperty.get();
    }

    public List<Property> readAll() {
        return propertyRepository.findAll();
    }

    public Boolean delete(String id) {
        if (StringUtils.isEmpty(id))
            return false;
        Property existingProperty = read(id);
        if (existingProperty == null) {
            return false;
        }
        propertyRepository.delete(existingProperty);
        return true;
    }

    public Property update(String id, Property property) {
        if (StringUtils.isEmpty(id))
            return null;
        Property existingProperty = read(id);
        if (existingProperty == null) {
            return null;
        }
        existingProperty.setCategory(property.getCategory());
        existingProperty.setCity(property.getCity());
        existingProperty.setCurrency(property.getCurrency());
        existingProperty.setPrice(property.getPrice());
        existingProperty.setTitle(property.getTitle());
        return propertyRepository.save(existingProperty);
    }

    public Property readByTitle(String title) {
        if (StringUtils.isEmpty(title))
            return null;
        Optional<Property> optionalProperty = propertyRepository.findByTitle(title);
        if (!optionalProperty.isPresent())
            return null;
        return optionalProperty.get();
    }

}
