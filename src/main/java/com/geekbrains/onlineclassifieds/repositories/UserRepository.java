package com.geekbrains.onlineclassifieds.repositories;

import com.geekbrains.onlineclassifieds.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
