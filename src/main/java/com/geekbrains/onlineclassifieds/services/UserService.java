package com.geekbrains.onlineclassifieds.services;

import com.geekbrains.onlineclassifieds.entities.User;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByUsername(String username);
    Optional<User> findUserByEmail(String email);
    void createUser(RegistrationUserDto registrationUserDto);
}
