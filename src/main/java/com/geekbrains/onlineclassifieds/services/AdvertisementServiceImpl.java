package com.geekbrains.onlineclassifieds.services;

import com.geekbrains.onlineclassifieds.converters.AdvertisementConverter;
import com.geekbrains.onlineclassifieds.dto.AdvertisementDto;
import com.geekbrains.onlineclassifieds.entities.Advertisement;
import com.geekbrains.onlineclassifieds.entities.User;
import com.geekbrains.onlineclassifieds.repositories.AdvertisementRepository;
import com.geekbrains.onlineclassifieds.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl {
    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementConverter advertisementConverter;
    private final UserRepository userRepository;

    public Advertisement saveNewAdvertisement(AdvertisementDto advertisementDto, String username) {
        User user = userRepository.findByUsername(username);
        advertisementDto.setExpirationDate(LocalDateTime.now().plusDays(1));
        advertisementDto.setIsPaid(false);
        advertisementDto.setIsDeleted(false);
        Advertisement advertisement = advertisementConverter.dtoToEntity(advertisementDto, user);
        advertisement.setId(null);
        return advertisementRepository.save(advertisement);
    }

    @Transactional
    public Advertisement updateAdvertisementInfo(AdvertisementDto advertisementDto) {
        Advertisement advertisement = advertisementRepository.findById(advertisementDto.getId()).orElseThrow(() -> new IllegalArgumentException("Can't update the product (not found in the DB) id: " + advertisementDto.getId()));
        advertisement.setTitle(advertisementDto.getTitle());
        advertisement.setDescription(advertisementDto.getDescription());
        advertisement.setCategory(advertisementDto.getCategory());
        return advertisement;
    }

    @Transactional
    public void updateToPaid(Long id) {
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Can't update the product (not found in the DB) id: " + id));
        advertisement.setIsPaid(true);
        advertisement.setPrice(BigDecimal.ONE); // ToDo: DB table with prices
        advertisement.setExpirationDate(advertisement.getExpirationDate().plusDays(7)); // ToDo: days added should depend on the payment
    }
}
