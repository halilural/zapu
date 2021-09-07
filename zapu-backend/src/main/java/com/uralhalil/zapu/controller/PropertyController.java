package com.uralhalil.zapu.controller;

import com.uralhalil.zapu.exception.NotFoundException;
import com.uralhalil.zapu.exception.QueryDSLPredicateBuildException;
import com.uralhalil.zapu.model.entity.Property;
import com.uralhalil.zapu.payload.PropertyResponse;
import com.uralhalil.zapu.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.UnknownHostException;


@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    PropertyService service;

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    @ResponseBody
    public Page<PropertyResponse> search(Pageable pageable, @RequestParam(value = "search") String search) throws QueryDSLPredicateBuildException, UnknownHostException, NoSuchMethodException {
        return service.search(pageable, search);
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/seo-interceptor")
//    public ResponseEntity<?> seoInterceptor() throws IOException {
//        String searchUrl = "http://localhost:8080/api/properties/search?search=title:test,title:halil";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(URI.create(searchUrl));
//        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
//    }

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
