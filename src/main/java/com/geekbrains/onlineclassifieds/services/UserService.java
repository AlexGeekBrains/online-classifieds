package com.geekbrains.onlineclassifieds.services;

import com.geekbrains.onlineclassifieds.entities.User;

public interface UserService {
    User findByUsername(String username);
}
