package com.uralhalil.zapu.predicate.builder;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.uralhalil.zapu.exception.QueryDSLPredicateBuildException;
import com.uralhalil.zapu.predicate.QueryDSLPredicate;
import com.uralhalil.zapu.predicate.util.SearchCriteria;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class QueryDSLPredicatesBuilder<T> {
    private final Set<SearchCriteria> searchCriteriaList;
    private final Class<T> entityClass;

    public QueryDSLPredicatesBuilder(Class<T> entityClass) {
        this.searchCriteriaList = new HashSet<>();
        this.entityClass = entityClass;
    }

    public QueryDSLPredicatesBuilder with(final String key, final String operation, final Object value) {
        searchCriteriaList.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public QueryDSLPredicatesBuilder with(final String search) {
        Pattern pattern = Pattern.compile("([^,]+)([:;!<>])([^,]+)");
        Matcher matcher = pattern.matcher(search);
        while (matcher.find()) {
            this.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        return this;
    }

    public BooleanExpression build() throws QueryDSLPredicateBuildException {
        if (searchCriteriaList.size() == 0) {
            return null;
        }
        final Map<String, BooleanExpression> predicates = new HashMap<>();
        QueryDSLPredicate<T> predicate;
        AtomicReference<BooleanExpression> result = new AtomicReference<>();
        for (final SearchCriteria param : searchCriteriaList) {
            predicate = new QueryDSLPredicate<>(param);
            final BooleanExpression exp = predicate.getPredicate(entityClass);
            if (exp != null) {
                if (predicates.containsKey(param.getKey())) {
                    predicates.put(param.getKey(), exp);
                    result.set(result.get().or(exp));
                } else {
                    predicates.put(param.getKey(), exp);
                    if (result.get() == null) {
                        result.set(exp);
                    }else{
                        result.set(result.get().and(exp));
                    }
                }
            }
        }
        return result.get();
    }
}