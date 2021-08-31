package com.uralhalil.zapu.repository;

import com.uralhalil.zapu.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(String role);
}
