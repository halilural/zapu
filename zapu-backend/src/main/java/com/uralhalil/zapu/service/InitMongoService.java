package com.uralhalil.zapu.service;

import com.uralhalil.zapu.model.entity.Role;
import com.uralhalil.zapu.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InitMongoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void init() {
        // Drop existing collections
        mongoTemplate.dropCollection("role");
        mongoTemplate.dropCollection("user");

        // Create new records
        Role adminRole = new Role();
        adminRole.setId(UUID.randomUUID().toString());
        adminRole.setRole("ADMIN");

        Role userRole = new Role();
        userRole.setId(UUID.randomUUID().toString());
        userRole.setRole("USER");

        User halil = new User();
        halil.setId(UUID.randomUUID().toString());
        halil.setFirstName("Halil");
        halil.setLastName("Ural");
        halil.setPassword(passwordEncoder.encode("123456"));
        halil.setRole(adminRole);
        halil.setUsername("halil");

        User zapu = new User();
        zapu.setId(UUID.randomUUID().toString());
        zapu.setFirstName("zapu");
        zapu.setLastName("zapu");
        zapu.setPassword(passwordEncoder.encode("123456"));
        zapu.setRole(userRole);
        zapu.setUsername("zapu");

        // Insert to db
        mongoTemplate.insert(halil, "user");
        mongoTemplate.insert(zapu, "user");
        mongoTemplate.insert(adminRole, "role");
        mongoTemplate.insert(userRole, "role");
    }
}
