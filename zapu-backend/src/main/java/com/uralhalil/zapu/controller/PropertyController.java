package com.uralhalil.zapu.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.uralhalil.zapu.exception.NotFoundException;
import com.uralhalil.zapu.exception.QueryDSLPredicateBuildException;
import com.uralhalil.zapu.model.Property;
import com.uralhalil.zapu.payload.PropertyResponse;
import com.uralhalil.zapu.predicate.builder.QueryDSLPredicatesBuilder;
import com.uralhalil.zapu.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    PropertyService service;

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    @ResponseBody
    public Page<PropertyResponse> search(Pageable pageable, @RequestParam(value = "search") String search) throws QueryDSLPredicateBuildException {
        QueryDSLPredicatesBuilder builder = new QueryDSLPredicatesBuilder(Property.class);
        if (search != null) {
            Pattern pattern = Pattern.compile("([^=,]+):([^,]*)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(search);
            while (matcher.find()) {
                builder.with(matcher.group(1), ":", matcher.group(2));
            }
        }
        BooleanExpression exp = builder.build();
        return service.search(pageable, exp);
    }

    @GetMapping
    public List<Property> getList() {
        return service.readAll();
    }

    @GetMapping("/{id}")
    public Property getSingle(@PathVariable("id") String id) {
        Property property = null;
        try {
            property = service.read(id);
        } catch (NotFoundException exception) {
        }
        return property;
    }

    @PutMapping("/{id}")
    public Property updateSingle(@PathVariable("id") String id, @Valid @RequestBody Property property) throws NotFoundException {
        return service.update(id, property);
    }

    @PostMapping
    public Property create(@Valid @RequestBody Property property) {
        return service.create(property);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) throws NotFoundException {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
