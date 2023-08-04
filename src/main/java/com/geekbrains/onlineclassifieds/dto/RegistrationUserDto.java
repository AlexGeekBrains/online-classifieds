package com.geekbrains.onlineclassifieds.dto;

public record RegistrationUserDto(String username, String displayName, String telephone, String email, String password, String confirmPassword) {}