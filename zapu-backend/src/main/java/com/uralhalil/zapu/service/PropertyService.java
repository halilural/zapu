package com.uralhalil.zapu.service;

import com.uralhalil.zapu.exception.NotFoundException;
import com.uralhalil.zapu.model.Currency;
import com.uralhalil.zapu.model.Property;
import com.uralhalil.zapu.repository.CategoryRepository;
import com.uralhalil.zapu.repository.CityRepository;
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

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void propertyInit() {
        create(Property.builder()
                .city(cityRepository.findByName("Antalya").get())
                .category(categoryRepository.findByName("konut").get())
                .title("test")
                .id(UUID.randomUUID().toString())
                .price(100.00)
                .currency(Currency.TL)
                .build());
    }

    public Property create(Property property) {
        if(property == null)
            return null;
        Property existing = null;
        try {
            existing = read(property.getId());
        } catch (NotFoundException exception) {

        }
        if (existing != null) {
            return null;
        }
        return propertyRepository.save(property);
    }

    public Property read(String id) throws NotFoundException {
        if (StringUtils.isEmpty(id))
            throw new NotFoundException();
        Optional<Property> optionalCategory = propertyRepository.findById(id);
        if (optionalCategory == null || !optionalCategory.isPresent())
            throw new NotFoundException("Property", "id", id);
        return optionalCategory.get();
    }

    public List<Property> readAll() {
        return propertyRepository.findAll();
    }

    public Boolean delete(String id) throws NotFoundException {
        if (StringUtils.isEmpty(id))
            return false;
        propertyRepository.delete(read(id));
        return true;
    }

    public Property update(String id, Property property) throws NotFoundException {
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
