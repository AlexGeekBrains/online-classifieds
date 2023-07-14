package com.geekbrains.onlineclassifieds.validators;

import com.geekbrains.onlineclassifieds.dto.RegistrationUserDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegistrationValidator {
    public void validate(RegistrationUserDto registrationUserDto) {
        List<String> errors = new ArrayList<>();
        if (registrationUserDto.username() == null || registrationUserDto.username().isBlank()) {
            errors.add("Username can't be blank");
        }
        if (registrationUserDto.password() == null || registrationUserDto.password().isBlank()) {
            errors.add("Password can't be blank");
        }
        if (registrationUserDto.confirmPassword() == null || registrationUserDto.confirmPassword().isBlank()) {
            errors.add("Password confirmation can't be blank");
        }
        if (!errors.isEmpty()) {
            System.out.println(errors); // @ToDo: errors log
            throw new IllegalArgumentException(String.join(", ", errors));
        }
    }
}
