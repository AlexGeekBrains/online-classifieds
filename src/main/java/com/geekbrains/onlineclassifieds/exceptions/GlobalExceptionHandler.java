package com.geekbrains.onlineclassifieds.exceptions;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<SingleError> catchEntityNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new SingleError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ListError> catchValidationException(FieldsValidationException e) {
        log.info(e.getMessage(), e);
        return new ResponseEntity<>(new ListError(HttpStatus.BAD_REQUEST.value(), e.getErrorFieldsMessages()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<SingleError> catchBadCredentialsException(BadCredentialsException e) {
        log.info(e.getMessage(), e);
        return new ResponseEntity<>(new SingleError(HttpStatus.UNAUTHORIZED.value(), e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<SingleError> catchDisabledException(DisabledException e) {
        log.warn(e.getMessage(), e);
        return new ResponseEntity<>(new SingleError(HttpStatus.FORBIDDEN.value(), e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<SingleError> catchAdvertisementOwnershipException(AdvertisementOwnershipException e) {
        log.warn("Advertisement ownership validation failed", e);
        return new ResponseEntity<>(new SingleError(HttpStatus.FORBIDDEN.value(), e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<SingleError> catchFreeLimitExceededException(FreeLimitExceededException e) {
        log.warn(e.getMessage(), e);
        return new ResponseEntity<>(new SingleError(HttpStatus.FORBIDDEN.value(), e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<SingleError> catchUnavailableAdvertisementException(UnavailableAdvertisementException e) {
        log.warn(e.getMessage(), e);
        return new ResponseEntity<>(new SingleError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}