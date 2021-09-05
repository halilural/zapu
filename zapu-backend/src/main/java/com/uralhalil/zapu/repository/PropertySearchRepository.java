package com.uralhalil.zapu.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.uralhalil.zapu.model.Property;
import com.uralhalil.zapu.model.QProperty;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PropertySearchRepository extends PagingAndSortingRepository<Property, String>, QuerydslPredicateExecutor<Property>, QuerydslBinderCustomizer<QProperty> {

    @Override
    default public void customize(
            QuerydslBindings bindings, QProperty root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.excluding(root.id);
    }

}
