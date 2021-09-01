package com.uralhalil.zapu.config;

import com.uralhalil.zapu.service.CategoryService;
import com.uralhalil.zapu.service.CityService;
import com.uralhalil.zapu.service.InitMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class StartupTask {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private InitMongoService initMongoService;

    @Autowired
    private CityService cityService;

    @PostConstruct
    private void executeDbOperation() {
        System.out.println("Kullanıcılar ve rolleri yükleniyor...");
        initMongoService.init();
        System.out.println("Kategoriler yükleniyor...");
        categoryService.categoryInit();
        System.out.println("Şehirler yükleniyor...");
        cityService.cityInit();
    }

}
