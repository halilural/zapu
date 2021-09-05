package com.uralhalil.zapu.service.integration;

import com.uralhalil.zapu.ZapuApplication;
import com.uralhalil.zapu.exception.NotFoundException;
import com.uralhalil.zapu.service.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.uralhalil.zapu.service.util.TestConstant.CATEGORY_NAME_BOOK;
import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;

@SpringBootTest(classes = ZapuApplication.class)
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @BeforeEach
    void setup() {
        try {
            categoryService.delete(CATEGORY_NAME_BOOK);
        } catch (NotFoundException exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }

    @AfterEach
    void tearDown() {
        try {
            categoryService.delete(CATEGORY_NAME_BOOK);
        } catch (NotFoundException exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }

    @Test
    void givenCategoryService_WhenInjected_ThenItShouldNotBeNull() {
        assertNotNull(categoryService);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void givenNullAndEmptyAndValuesAsCategoryName_WhenCategoryCreated_ThenItShouldNotBeCreated(String name) {
        assertNotNull(categoryService.create(name));
    }

    @Test
    void givenNameAsCategoryName_WhenCategoryCreated_ThenItShouldBeCreated() {
        String name = CATEGORY_NAME_BOOK;
        assertNotNull(categoryService.create(name));
    }

    @Test
    void givenNameAsCategoryName_WhenCategoryRead_ThenItShouldBeReturned() throws NotFoundException {
        String name = CATEGORY_NAME_BOOK;
        // First create
        assertNotNull(categoryService.create(name));
        //Then read it
        assertNotNull(categoryService.read(name));
    }

    @Test
    void givenNothing_WhenCategoryListRead_ThenItShouldBeReturned() {
        assertNotNull(categoryService.readAll());
    }

    @Test
    void givenNameAsCategoryNameWhenCategoryCreated_ThenItShouldBeCreatedWithoutTimeout() {
        String name = CATEGORY_NAME_BOOK;
        assertTimeout(ofMillis(2000), () -> {
            categoryService.create(name);
        });
    }
}