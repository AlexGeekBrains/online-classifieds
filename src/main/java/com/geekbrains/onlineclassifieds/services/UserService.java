package com.geekbrains.onlineclassifieds.services;

import com.geekbrains.onlineclassifieds.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByUsername(String username);
}
