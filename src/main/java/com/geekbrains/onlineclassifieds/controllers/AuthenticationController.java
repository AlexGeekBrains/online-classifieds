package com.geekbrains.onlineclassifieds.controllers;

import com.geekbrains.onlineclassifieds.dto.JwtRequest;
import com.geekbrains.onlineclassifieds.dto.JwtResponse;
import com.geekbrains.onlineclassifieds.dto.RegistrationResponseDto;
import com.geekbrains.onlineclassifieds.dto.RegistrationUserDto;
import com.geekbrains.onlineclassifieds.services.UserService;
import com.geekbrains.onlineclassifieds.utils.JwtTokenUtil;
import com.geekbrains.onlineclassifieds.validators.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final RegistrationValidator registrationValidator;

    @PostMapping()
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws DisabledException,BadCredentialsException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponseDto> registerUser(@RequestBody RegistrationUserDto registrationUserDto) {
        registrationValidator.validate(registrationUserDto); // Front should be controlling it too.
        userService.createUser(registrationUserDto);
        return ResponseEntity.ok(new RegistrationResponseDto("Registration successful! Please, log in."));
    }
}