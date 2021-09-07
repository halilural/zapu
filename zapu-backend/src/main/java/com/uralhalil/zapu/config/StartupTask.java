package com.uralhalil.zapu.config;

import com.uralhalil.zapu.model.entity.RootUrlConfig;
import com.uralhalil.zapu.repository.RootUrlConfigRepository;
import com.uralhalil.zapu.service.CategoryService;
import com.uralhalil.zapu.service.CityService;
import com.uralhalil.zapu.service.InitMongoService;
import com.uralhalil.zapu.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StartupTask {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private InitMongoService initMongoService;

    @Autowired
    private CityService cityService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private RootUrlConfigRepository rootUrlConfigRepository;

    @PostConstruct
    private void executeDbOperation() {
        System.out.println("Kullanıcılar ve rolleri yükleniyor...");
        initMongoService.init();
        System.out.println("Kategoriler yükleniyor...");
        categoryService.categoryInit();
        System.out.println("Şehirler yükleniyor...");
        cityService.cityInit();
        System.out.println("RootUrlConfig yükleniyor...");
        List<RootUrlConfig> configs = new ArrayList<>();
        if (!rootUrlConfigRepository.findByParameterName("category").isPresent())
            configs.add(new RootUrlConfig(UUID.randomUUID().toString(), 1, "category", null, "CategoryRepository"));
        if (!rootUrlConfigRepository.findByParameterName("city").isPresent())
            configs.add(new RootUrlConfig(UUID.randomUUID().toString(), 2, "city", "category", "CityRepository"));
        rootUrlConfigRepository.saveAll(configs);
    }


}
