package com.uralhalil.zapu.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.uralhalil.zapu.exception.NotFoundException;
import com.uralhalil.zapu.exception.QueryDSLPredicateBuildException;
import com.uralhalil.zapu.model.entity.Currency;
import com.uralhalil.zapu.model.entity.Property;
import com.uralhalil.zapu.model.entity.RootUrlConfig;
import com.uralhalil.zapu.model.pojo.UrlParam;
import com.uralhalil.zapu.payload.PropertyResponse;
import com.uralhalil.zapu.predicate.builder.QueryDSLPredicatesBuilder;
import com.uralhalil.zapu.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    private static final String REPOSITORY_PACKAGE_NAME = "com.uralhalil.zapu.repository";

    @Autowired
    private ApplicationContext context;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertySearchRepository propertySearchRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RootUrlConfigRepository rootUrlConfigRepository;

    public void propertyInit() {
        create(Property.builder()
                .city(cityRepository.findByName("Ankara").get().getId())
                .category(categoryRepository.findByName("konut").get().getId())
                .title("test")
                .id(UUID.randomUUID().toString())
                .price(100.00)
                .currency(Currency.TL)
                .build());
    }

    public Property create(Property property) {
        if (property == null)
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

    public Page<PropertyResponse> search(Pageable pageable, String search, String url) throws QueryDSLPredicateBuildException, UnknownHostException, NoSuchMethodException {
        QueryDSLPredicatesBuilder builder = new QueryDSLPredicatesBuilder(Property.class);
        TreeMap<UrlParam, String> parameters = new TreeMap<>();
        String rootUrl = "";
        List<RootUrlConfig> configs = rootUrlConfigRepository.findAll();
        boolean isUrlHasDuplicateKeys = false;
        if (search != null) {
            Pattern pattern = Pattern.compile("([^=,]+):([^,]*)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(search);
            while (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2);
                builder.with(key, ":", value);
                // add key to map for rootUrl
                Optional<RootUrlConfig> rootUrlConfig;
                rootUrlConfig = configs.stream()
                        .filter(rootUrlConfig1 -> rootUrlConfig1.getParameterName()
                                .equals(key)).findAny();
                if (rootUrlConfig.isPresent()) {
                    String val = parameters.put(new UrlParam(key, rootUrlConfig.get().getPriority()), value);
                    if (val != null)
                        isUrlHasDuplicateKeys = true;
                }
            }
        }

        //root url
        if (isUrlHasDuplicateKeys)
            rootUrl = null;
        else {
            rootUrl = generateRootUrl(parameters, url);
        }
        BooleanExpression exp = builder.build();
        Page<Property> propertyPage;
        if (exp == null)
            propertyPage = propertySearchRepository.findAll(pageable);
        else {
            propertyPage = propertySearchRepository.findAll(exp, pageable);
        }
        if (propertyPage == null)
            return null;
        int totalElements = (int) propertyPage.getTotalElements();
        String finalRootUrl = rootUrl;
        return new PageImpl<PropertyResponse>(propertyPage.getContent()
                .stream()
                .map(property -> {
                    PropertyResponse response = PropertyResponse.builder()
                            .id(property.getId())
                            .category(property.getCategory())
                            .city(property.getCity())
                            .currency(property.getCurrency())
                            .price(property.getPrice())
                            .title(property.getTitle())
                            .rootUrl(finalRootUrl)
                            .build();
                    return response;
                })
                .collect(Collectors.toList()), pageable, totalElements);
    }

    private String generateRootUrl(TreeMap<UrlParam, String> parameters, String url) throws NoSuchMethodException {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(url);
        List<RootUrlConfig> configs = rootUrlConfigRepository.findAll();
        // Are priority being applied properly ? /category/city/e.t.c
        for (Map.Entry<UrlParam, String> entry : parameters.entrySet()) {
            Optional<RootUrlConfig> optionalRootUrlConfig = configs.stream()
                    .filter(rootUrlConfig -> rootUrlConfig.getParameterName().equals(entry.getKey().getName()))
                    .findAny();
            RootUrlConfig rootUrlConfig = optionalRootUrlConfig.get();
            String upParameter = rootUrlConfig.getUpParameterName();
            UrlParam upUrlParam = new UrlParam(upParameter, rootUrlConfig.getPriority());
            if (upParameter != null && !parameters.containsKey(upUrlParam)) {
                return null;
            }
            // Dynamically call repository method and get name val
            try {
                Object o = context.getBean(Class.forName(REPOSITORY_PACKAGE_NAME + "." + rootUrlConfig.getRepositoryName()));
                if (o instanceof CrudRepository) {
                    CrudRepository crudRepository = (CrudRepository) o;
                    Optional optional = crudRepository.findById(entry.getValue());
                    if (optional.isPresent()) {
                        Method getNameMethod = optional.get().getClass().getMethod("getName");
                        String name = (String) getNameMethod.invoke(optional.get());
                        urlBuilder.append("/" + name);
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return urlBuilder.toString();
    }
}
