package com.uralhalil.zapu.service.integration;

import com.uralhalil.zapu.ZapuApplication;
import com.uralhalil.zapu.exception.NotFoundException;
import com.uralhalil.zapu.model.Currency;
import com.uralhalil.zapu.model.Property;
import com.uralhalil.zapu.service.CategoryService;
import com.uralhalil.zapu.service.CityService;
import com.uralhalil.zapu.service.PropertyService;
import com.uralhalil.zapu.service.util.TestConstant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static com.uralhalil.zapu.service.util.TestConstant.*;
import static java.time.Duration.ofMillis;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;

@SpringBootTest(classes = ZapuApplication.class)
class PropertyServiceTest {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CityService cityService;

    final Property RECORD_1 = new Property(UUID.randomUUID().toString(), PROPERTY_NAME_BINA, 1000.00, Currency.TL);
    final Property RECORD_2 = new Property(UUID.randomUUID().toString(), PROPERTY_NAME_APARTMAN, 1500.00, Currency.TL);
    final Property RECORD_3 = new Property(UUID.randomUUID().toString(), PROPERTY_NAME_GECEKONDU, 300.00, Currency.TL);


    @Test
    void givenPropertyService_WhenInjected_ThenItShouldNotBeNull() {
        assertNotNull(propertyService);
    }

    @BeforeEach
    void setup() {
        try {
            categoryService.delete(TestConstant.CATEGORY_NAME_BOOK);
        } catch (NotFoundException exception) {

        }
        try {
            cityService.delete(TestConstant.CITY_NAME_IZMIR);
        } catch (NotFoundException exception) {

        }
        Assertions.assertNotNull(categoryService.create(TestConstant.CATEGORY_NAME_BOOK));
        Assertions.assertNotNull(cityService.create(TestConstant.CITY_NAME_IZMIR));
    }

    @AfterEach
    void tearDown() {
        try {
            categoryService.delete(TestConstant.CATEGORY_NAME_BOOK);
        } catch (NotFoundException exception) {

        }
        try {
            cityService.delete(TestConstant.CITY_NAME_IZMIR);
        } catch (NotFoundException exception) {

        }
        try {
            propertyService.delete(RECORD_1.getId());
        } catch (NotFoundException exception) {

        }
    }

    @ParameterizedTest
    @NullSource
    void givenNullAsProperty_WhenCreated_ThenItShouldNotBeCreated(Property property) {
        assertNull(propertyService.create(property));
    }

    @Test
    void givenProperty_WhenCreated_ThenItShouldBeCreated() throws NotFoundException {
        RECORD_1.setCity(cityService.read(CITY_NAME_IZMIR).getId());
        RECORD_1.setCategory(categoryService.read(CATEGORY_NAME_BOOK).getId());
        assertNotNull(propertyService.create(RECORD_1));
    }

    @Test
    void givenId_WhenReadById_ThenItShouldBeReturned() throws NotFoundException {
        // First, create property
        RECORD_1.setCity(cityService.read(CITY_NAME_IZMIR).getId());
        RECORD_1.setCategory(categoryService.read(CATEGORY_NAME_BOOK).getId());
        assertNotNull(propertyService.create(RECORD_1));
        //then read
        assertNotNull(propertyService.read(RECORD_1.getId()));
    }

    @Test
    void givenNothing_WhenPropertyListRead_ThenItShouldBeReturned() {
        assertNotNull(propertyService.readAll());
    }

    @Test
    void givenNameAsPropertyNameWhenPropertyCreated_ThenItShouldBeCreatedWithoutTimeout() throws NotFoundException {
        RECORD_1.setCity(cityService.read(CITY_NAME_IZMIR).getId());
        RECORD_1.setCategory(categoryService.read(CATEGORY_NAME_BOOK).getId());
        assertTimeout(ofMillis(2000), () -> {
            propertyService.create(RECORD_1);
        });
    }
}