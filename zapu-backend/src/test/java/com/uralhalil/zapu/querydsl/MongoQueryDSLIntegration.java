package com.uralhalil.zapu.querydsl;

import com.uralhalil.zapu.ZapuApplication;
import com.uralhalil.zapu.exception.NotFoundException;
import com.uralhalil.zapu.exception.QueryDSLPredicateBuildException;
import com.uralhalil.zapu.model.entity.Currency;
import com.uralhalil.zapu.model.entity.Property;
import com.uralhalil.zapu.predicate.builder.QueryDSLPredicatesBuilder;
import com.uralhalil.zapu.repository.PropertyRepository;
import com.uralhalil.zapu.service.PropertyService;
import com.uralhalil.zapu.service.util.TestConstant;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static com.uralhalil.zapu.service.util.TestConstant.PROPERTY_NAME_APARTMAN;
import static com.uralhalil.zapu.service.util.TestConstant.PROPERTY_NAME_BINA;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = ZapuApplication.class)
public class MongoQueryDSLIntegration {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyRepository repo;

    final Property RECORD_1 = new Property(UUID.randomUUID().toString(), PROPERTY_NAME_BINA, 1000.00, Currency.TL);
    final Property RECORD_2 = new Property(UUID.randomUUID().toString(), PROPERTY_NAME_APARTMAN, 1500.00, Currency.TL);

    @BeforeEach
    public void init() {
        try {
            propertyService.delete(TestConstant.PROPERTY_NAME_BINA);
        } catch (NotFoundException exception) {

        }

        try {
            propertyService.delete(TestConstant.PROPERTY_NAME_APARTMAN);
        } catch (NotFoundException exception) {

        }

        Assert.assertNotNull(propertyService.create(RECORD_1));
        Assert.assertNotNull(propertyService.create(RECORD_2));

    }

    @AfterEach
    public void tearDown() {
        try {
            propertyService.delete(RECORD_1.getId());
        } catch (NotFoundException exception) {

        }
        try {
            propertyService.delete(RECORD_2.getId());
        } catch (NotFoundException exception) {

        }
    }


    @Test
    public void givenLast_whenGettingListOfProperty_thenCorrect() throws QueryDSLPredicateBuildException {
        QueryDSLPredicatesBuilder builderBina = new QueryDSLPredicatesBuilder(Property.class)
                .with("title", ":", RECORD_1.getTitle());
        QueryDSLPredicatesBuilder builderApartman = new QueryDSLPredicatesBuilder(Property.class)
                .with("title", ":", RECORD_2.getTitle());
        Iterable<Property> results = repo.findAll(builderBina.build().or(builderApartman.build()));
        assertThat(results, containsInAnyOrder(RECORD_1, RECORD_2));
    }

//    @Test
//    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
//        MyUserPredicatesBuilder builder = new MyUserPredicatesBuilder()
//                .with("firstName", ":", "John").with("lastName", ":", "Doe");
//
//        Iterable<MyUser> results = repo.findAll(builder.build());
//
//        assertThat(results, contains(userJohn));
//        assertThat(results, not(contains(userTom)));
//    }

//    @Test
//    public void givenLastAndAge_whenGettingListOfUsers_thenCorrect() {
//        MyUserPredicatesBuilder builder = new MyUserPredicatesBuilder()
//                .with("lastName", ":", "Doe").with("age", ">", "25");
//
//        Iterable<MyUser> results = repo.findAll(builder.build());
//
//        assertThat(results, contains(userTom));
//        assertThat(results, not(contains(userJohn)));
//    }
//
//    @Test
//    public void givenWrongFirstAndLast_whenGettingListOfUsers_thenCorrect() {
//        MyUserPredicatesBuilder builder = new MyUserPredicatesBuilder()
//                .with("firstName", ":", "Adam").with("lastName", ":", "Fox");
//
//        Iterable<MyUser> results = repo.findAll(builder.build());
//        assertThat(results, emptyIterable());
//    }
//
//    @Test
//    public void givenPartialFirst_whenGettingListOfUsers_thenCorrect() {
//        MyUserPredicatesBuilder builder = new MyUserPredicatesBuilder().with("firstName", ":", "jo");
//
//        Iterable<MyUser> results = repo.findAll(builder.build());
//
//        assertThat(results, contains(userJohn));
//        assertThat(results, not(contains(userTom)));
//    }


}
