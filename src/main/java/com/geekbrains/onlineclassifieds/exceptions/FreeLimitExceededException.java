package com.geekbrains.onlineclassifieds.exceptions;

public class FreeLimitExceededException extends RuntimeException {
    public FreeLimitExceededException(String message) {
        super(message);
    }
}
