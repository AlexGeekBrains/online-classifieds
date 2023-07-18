package com.geekbrains.onlineclassifieds.controllers;

import com.geekbrains.onlineclassifieds.entities.Advertisement;
import com.geekbrains.onlineclassifieds.entities.User;
import com.geekbrains.onlineclassifieds.services.AdvertisementServiceImpl;
import com.geekbrains.onlineclassifieds.services.UserService;
import com.geekbrains.onlineclassifieds.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final UserServiceImpl userServiceImpl;

    @GetMapping()
    public List<User> showUsers() {
        return userServiceImpl.getAllUsers();
    }

}
