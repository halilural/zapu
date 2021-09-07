package com.uralhalil.zapu.controller;

import com.uralhalil.zapu.exception.NotFoundException;
import com.uralhalil.zapu.exception.QueryDSLPredicateBuildException;
import com.uralhalil.zapu.model.entity.Property;
import com.uralhalil.zapu.payload.PropertyResponse;
import com.uralhalil.zapu.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.List;


@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    PropertyService service;

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    @ResponseBody
    public ResponseEntity search(Pageable pageable, @RequestParam(value = "search") String search,HttpServletRequest request)
            throws QueryDSLPredicateBuildException, UnknownHostException, NoSuchMethodException {
        Page<PropertyResponse> page = service.search(pageable, search,request.getRequestURL().toString().split("/api")[0]);
        String rootUrl = null;
        if (page != null) {
            List<PropertyResponse> list = page.getContent();
            if (!list.isEmpty()) {
                rootUrl = list.get(0).getRootUrl();
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(rootUrl));
        return new ResponseEntity<>(page, headers, HttpStatus.MOVED_PERMANENTLY);
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
