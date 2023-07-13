package com.geekbrains.onlineclassifieds.services;

import com.geekbrains.onlineclassifieds.entities.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(String name);
}
