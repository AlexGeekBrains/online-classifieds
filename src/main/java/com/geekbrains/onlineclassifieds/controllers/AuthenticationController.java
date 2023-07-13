package com.geekbrains.onlineclassifieds.controllers;

import com.geekbrains.onlineclassifieds.dto.JwtRequest;
import com.geekbrains.onlineclassifieds.dto.JwtResponse;
import com.geekbrains.onlineclassifieds.dto.RegistrationUserDto;
import com.geekbrains.onlineclassifieds.services.UserService;
import com.geekbrains.onlineclassifieds.utils.JwtTokenUtil;
import com.geekbrains.onlineclassifieds.validators.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final UserService userDetailsService;
    private final RegistrationValidator registrationValidator;

    @PostMapping()
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        try {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws DisabledException,BadCredentialsException {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationUserDto registrationUserDto) {
        registrationValidator.validate(registrationUserDto); // ToDo: Front should be controlling it too
        if (!registrationUserDto.password().equals(registrationUserDto.confirmPassword())) {
            return new ResponseEntity<>("Passwords don't match, please try again!", HttpStatus.BAD_REQUEST);
        }
        if (userDetailsService.findByUsername(registrationUserDto.username()).isPresent()) {
            return new ResponseEntity<>("Username is taken, please try another one!", HttpStatus.BAD_REQUEST);
        }
        if (userDetailsService.findUserByEmail(registrationUserDto.email()).isPresent()) {
            return new ResponseEntity<>("Email is already registered, please restore password if needed!", HttpStatus.BAD_REQUEST);
        }
        userDetailsService.createUser(registrationUserDto);
        return ResponseEntity.ok("Registration successful! Please, log in.");
    }
}
