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

import static java.time.Duration.ofMillis;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest(classes = ZapuApplication.class)
class PropertyServiceTest {

//    @Autowired
//    private PropertyService propertyService;
//
//    @Autowired
//    private CategoryService categoryService;
//
//    @Autowired
//    private CityService cityService;
//
//    @Test
//    void givenPropertyService_WhenInjected_ThenItShouldNotBeNull() {
//        assertNotNull(propertyService);
//    }
//
//    @BeforeEach
//    void setup() {
//        try {
//            categoryService.delete(TestConstant.CATEGORY_NAME_BOOK);
//        } catch (NotFoundException exception) {
//
//        }
//        try {
//            cityService.delete(TestConstant.CITY_NAME_IZMIR);
//        } catch (NotFoundException exception) {
//
//        }
//        Assertions.assertNotNull(categoryService.create(TestConstant.CATEGORY_NAME_BOOK) != null);
//        Assertions.assertNotNull(cityService.create(TestConstant.CITY_NAME_IZMIR));
//    }
//
//    @AfterEach
//    void tearDown() {
//        try {
//            categoryService.delete(TestConstant.CATEGORY_NAME_BOOK);
//        } catch (NotFoundException exception) {
//
//        }
//        try {
//            cityService.delete(TestConstant.CITY_NAME_IZMIR);
//        } catch (NotFoundException exception) {
//
//        }
//    }
//
//    @ParameterizedTest
//    @NullSource
//    void givenNullAsProperty_WhenCreated_ThenItShouldNotBeCreated(Property property) {
//        assertTrue(propertyService.create(property));
//    }
//
//    @Test
//    void givenProperty_WhenCreated_ThenItShouldBeCreated() throws NotFoundException {
//        // Create Category
//        categoryService.create("CD");
//        category = categoryService.read("CD");
//        assumeTrue(category != null);
//        // Create City
//        cityService.create("İzmir");
//        city = cityService.read("İzmir");
//        assumeTrue(city != null);
//        // Create Property
//        Property property = Property.builder()
//                .category(category)
//                .city(city)
//                .currency(Currency.TL)
//                .price(60.00)
//                .title("Blue CD")
//                .build();
//        assertTrue(propertyService.create(property));
//    }
//
//    @Test
//    void givenNameAsPropertyName_WhenReadByTitle_ThenItShouldBeReturned() {
//        String title = "Blue CD";
//        assertNotNull(propertyService.readByTitle(title));
//    }
//
//    @Test
//    void givenNothing_WhenPropertyListRead_ThenItShouldBeReturned() {
//        assertNotNull(propertyService.readAll());
//    }
//
//    @Test
//    void givenNameAsPropertyNameWhenPropertyCreated_ThenItShouldBeCreatedWithoutTimeout() {
//        Property property = Property.builder()
//                .category(category)
//                .city(city)
//                .currency(Currency.TL)
//                .price(60.00)
//                .title("Blue CD")
//                .build();
//        assertTimeout(ofMillis(2000), () -> {
//            propertyService.create(property);
//        });
//    }
}