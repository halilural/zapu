package com.uralhalil.zapu.service;

import com.uralhalil.zapu.ZapuApplication;
import com.uralhalil.zapu.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;

@SpringBootTest(classes = ZapuApplication.class)
class CityServiceTest {

    @Autowired
    private CityService cityService;

    @Test
    void givenCityService_WhenInjected_ThenItShouldNotBeNull() {
        assertNotNull(cityService);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void givenNullAndEmptyAndValuesAsCityName_WhenCityCreated_ThenItShouldNotBeCreated(String name) {
        assertNotNull(cityService.create(name));
    }

    @Test
    void givenNameAsCityName_WhenCityCreated_ThenItShouldBeCreated() {
        String name = "Tokat";
        assertNotNull(cityService.create(name));
    }

    @Test
    void givenNameAsCityName_WhenCityRead_ThenItShouldBeReturned() throws NotFoundException {
        String name = "Tokat";
        assertNotNull(cityService.read(name));
    }

    @Test
    void givenNothing_WhenCityListRead_ThenItShouldBeReturned() {
        assertNotNull(cityService.readAll());
    }

    @Test
    void givenNameAsCityNameWhenCityCreated_ThenItShouldBeCreatedWithoutTimeout() {
        String name = "Tokat";
        assertTimeout(ofMillis(2000), () -> {
            cityService.create(name);
        });
    }
}