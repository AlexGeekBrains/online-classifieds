package com.geekbrains.onlineclassifieds.services;

import com.geekbrains.onlineclassifieds.entities.User;
import com.geekbrains.onlineclassifieds.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
