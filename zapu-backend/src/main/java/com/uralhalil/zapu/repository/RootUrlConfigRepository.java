package com.uralhalil.zapu.repository;

import com.uralhalil.zapu.model.entity.RootUrlConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RootUrlConfigRepository extends MongoRepository<RootUrlConfig, String> {
    Optional<RootUrlConfig> findByParameterName(String parameterName);
}
