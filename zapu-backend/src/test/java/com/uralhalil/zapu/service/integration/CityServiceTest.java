package com.uralhalil.zapu.service.integration;

import com.uralhalil.zapu.ZapuApplication;
import com.uralhalil.zapu.exception.NotFoundException;
import com.uralhalil.zapu.service.CityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.uralhalil.zapu.service.util.TestConstant.CITY_NAME_IZMIR;
import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;

@SpringBootTest(classes = ZapuApplication.class)
class CityServiceTest {

    @Autowired
    private CityService cityService;

    @BeforeEach
    void setup() {
        try {
            cityService.delete(CITY_NAME_IZMIR);
        } catch (NotFoundException exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }

    @AfterEach
    void tearDown() {
        try {
            cityService.delete(CITY_NAME_IZMIR);
        } catch (NotFoundException exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }

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
        String name = CITY_NAME_IZMIR;
        assertNotNull(cityService.create(name));
    }

    @Test
    void givenNameAsCityName_WhenCityRead_ThenItShouldBeReturned() throws NotFoundException {
        // First create
        String name = CITY_NAME_IZMIR;
        assertNotNull(cityService.create(name));
        //Then, read it
        assertNotNull(cityService.read(name));
    }

    @Test
    void givenNothing_WhenCityListRead_ThenItShouldBeReturned() {
        assertNotNull(cityService.readAll());
    }

    @Test
    void givenNameAsCityNameWhenCityCreated_ThenItShouldBeCreatedWithoutTimeout() {
        String name = CITY_NAME_IZMIR;
        assertTimeout(ofMillis(2000), () -> {
            cityService.create(name);
        });
    }
}