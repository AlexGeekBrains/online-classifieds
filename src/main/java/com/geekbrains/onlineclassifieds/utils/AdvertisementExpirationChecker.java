package com.geekbrains.onlineclassifieds.utils;

import com.geekbrains.onlineclassifieds.services.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AdvertisementExpirationChecker {
    private final AdvertisementService advertisementService;

    @Scheduled(fixedDelay = 600000) // Запуск каждые 10 минут (600 000 миллисекунд)
    public void updateExpiredAdvertisements() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        advertisementService.updateExpiredAdvertisements(currentDateTime, 10);
    }
}
