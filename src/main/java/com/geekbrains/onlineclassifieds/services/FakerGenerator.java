package com.geekbrains.onlineclassifieds.services;

import com.geekbrains.onlineclassifieds.entities.Advertisement;
import com.geekbrains.onlineclassifieds.entities.Category;
import com.geekbrains.onlineclassifieds.entities.User;
import com.geekbrains.onlineclassifieds.repositories.AdvertisementRepository;
import com.geekbrains.onlineclassifieds.repositories.CategoryRepository;
import com.geekbrains.onlineclassifieds.repositories.UserRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// That class is just for some fun testing. Disable if it's not needed for your purposes.
@Component
@RequiredArgsConstructor
public class FakerGenerator {
    private static final Faker faker = new Faker();

    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void generateProductsOnStartup() {
        User user = userRepository.findById(1L).orElseThrow();
        Category category = categoryRepository.findById(1L).orElseThrow();
        for (int i = 0; i < 20; i++) {
            advertisementRepository.save(generate(user, category));
        }
        System.out.println(advertisementRepository.countByUserAndIsPaidAndIsDeleted(user, false, false));
    }
    public static Advertisement generate(User user, Category category) {
        Advertisement advertisement = new Advertisement();
        advertisement.setTitle(faker.beer().name());
        advertisement.setDescription(faker.address().fullAddress());
        advertisement.setUserPrice(BigDecimal.valueOf(faker.number().randomDouble(2, 5, 500)));
        advertisement.setIsPaid(false);
        advertisement.setIsDeleted(false);
        advertisement.setExpirationDate(LocalDateTime.now().plusDays(1));
        advertisement.setUser(user);
        advertisement.setCategory(category);
        return advertisement;
    }
}