package com.geekbrains.onlineclassifieds.converters;

import com.geekbrains.onlineclassifieds.dto.UserDto;
import com.geekbrains.onlineclassifieds.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserConverter {

    public UserDto entityToDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }
}