package com.geekbrains.onlineclassifieds.exceptions;

public class UnavailableAdvertisement extends RuntimeException{
    public UnavailableAdvertisement(String message) {
        super(message);
    }
}
