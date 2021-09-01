package com.uralhalil.zapu.repository;

import com.uralhalil.zapu.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
