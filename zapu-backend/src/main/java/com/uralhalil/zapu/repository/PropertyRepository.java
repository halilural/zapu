package com.uralhalil.zapu.repository;

import com.uralhalil.zapu.model.entity.Property;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface PropertyRepository extends MongoRepository<Property, String>, QuerydslPredicateExecutor<Property> {
    Optional<Property> findByTitle(String title);

//    @Override
//    default public void customize(
//            QuerydslBindings bindings, QProperty root) {
//        bindings.bind(String.class)
//                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
//        bindings.excluding(root.id);
//    }

}
