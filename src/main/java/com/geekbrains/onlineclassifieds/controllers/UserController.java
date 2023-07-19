package com.geekbrains.onlineclassifieds.controllers;

import com.geekbrains.onlineclassifieds.converters.UserConverter;
import com.geekbrains.onlineclassifieds.dto.UserDto;
import com.geekbrains.onlineclassifieds.entities.User;
import com.geekbrains.onlineclassifieds.services.UserService;
import com.geekbrains.onlineclassifieds.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@RestController
@RequestMapping("api/v1/users")
@Component
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;
    @GetMapping("/get-users")
    public ResponseEntity<Page<UserDto>> filterUsers(
            @RequestParam(required = false) String partUsername,
            @RequestParam(required = false) String partEmail,
            @RequestParam(required = false) LocalDateTime partCreatedAt,
            @RequestParam(required = false) LocalDateTime partUpdatedAt,
            @RequestParam(defaultValue = "0") Integer page) {
        Page<UserDto> filteredUsers = userService.findAllUserWithFilter(partUsername, partEmail, partCreatedAt, partUpdatedAt, page);
        return ResponseEntity.ok(filteredUsers);
    }
}
