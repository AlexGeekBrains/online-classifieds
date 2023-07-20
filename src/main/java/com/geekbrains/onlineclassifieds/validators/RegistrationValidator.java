package com.geekbrains.onlineclassifieds.validators;

import com.geekbrains.onlineclassifieds.exceptions.FieldsValidationException;
import com.geekbrains.onlineclassifieds.dto.RegistrationUserDto;
import com.geekbrains.onlineclassifieds.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RegistrationValidator {
    private final UserService userService;
    public void validate(RegistrationUserDto registrationUserDto) {
        List<String> errors = new ArrayList<>();
        if (userService.findByUsername(registrationUserDto.username()).isPresent()) {
            errors.add("Username is taken, please try another one!");
        }
        if (userService.findUserByEmail(registrationUserDto.email()).isPresent()) {
            errors.add("Email is already registered, please restore password if needed!");
        }
        if (!errors.isEmpty()) {
            throw new FieldsValidationException(errors);
        }
        if (registrationUserDto.username() == null || registrationUserDto.username().isBlank()) {
            errors.add("Username can't be blank");
        }
        if (registrationUserDto.password() == null || registrationUserDto.password().isBlank()) {
            errors.add("Password can't be blank");
        } else if (!registrationUserDto.password().equals(registrationUserDto.confirmPassword())) {
                errors.add("Passwords don't match, please try again!");
        }
        if (registrationUserDto.confirmPassword() == null || registrationUserDto.confirmPassword().isBlank()) {
            errors.add("Password confirmation can't be blank");
        }
        if (!errors.isEmpty()) {
            throw new FieldsValidationException(errors);
        }
    }
}
