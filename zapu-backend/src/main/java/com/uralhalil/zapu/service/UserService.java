package com.uralhalil.zapu.service;

import com.uralhalil.zapu.model.entity.User;
import com.uralhalil.zapu.repository.RoleRepository;
import com.uralhalil.zapu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User create(User user) {
        user.setId(UUID.randomUUID().toString());
        user.getRole().setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // We must save both separately since there is no cascading feature
        // in Spring Data MongoDB (for now)
        roleRepository.save(user.getRole());
        return userRepository.save(user);
    }

    public User read(User user) {
        return user;
    }

    public List<User> readAll() {
        return userRepository.findAll();
    }

    public User update(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser == null) {
            return null;
        }
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.getRole().setRole(user.getRole().getRole());
        // We must save both separately since there is no cascading feature
        // in Spring Data MongoDB (for now)
        roleRepository.save(existingUser.getRole());
        return userRepository.save(existingUser);
    }

    public Boolean delete(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser == null) {
            return false;
        }

        // We must delete both separately since there is no cascading feature
        // in Spring Data MongoDB (for now)
        roleRepository.delete(existingUser.getRole());
        userRepository.delete(existingUser);
        return true;
    }

}
