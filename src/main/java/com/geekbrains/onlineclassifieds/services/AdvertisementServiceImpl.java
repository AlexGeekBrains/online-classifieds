package com.geekbrains.onlineclassifieds.services;

import com.geekbrains.onlineclassifieds.converters.AdvertisementConverter;
import com.geekbrains.onlineclassifieds.dto.AdvertisementDto;
import com.geekbrains.onlineclassifieds.entities.Advertisement;
import com.geekbrains.onlineclassifieds.entities.User;
import com.geekbrains.onlineclassifieds.repositories.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementConverter advertisementConverter;
    private final UserService userService;

    @Override
    public Advertisement saveNewAdvertisement(AdvertisementDto advertisementDto, String username) {
        User user = userService.findByUsername(username);
        advertisementDto.setExpirationDate(LocalDateTime.now().plusDays(1));
        advertisementDto.setIsPaid(false);
        advertisementDto.setIsDeleted(false);
        Advertisement advertisement = advertisementConverter.dtoToEntity(advertisementDto, user);
        advertisement.setId(null);
        advertisement.setCategory(null); // ToDo: temporary, need to decide how to work with categories
        return advertisementRepository.save(advertisement);
    }

    @Override
    @Transactional
    public Advertisement updateAdvertisementInfo(AdvertisementDto advertisementDto) {
        Advertisement advertisement = advertisementRepository.findById(advertisementDto.getId()).orElseThrow(() -> new IllegalArgumentException("Can't update the product (not found in the DB) id: " + advertisementDto.getId()));
        advertisement.setTitle(advertisementDto.getTitle());
        advertisement.setDescription(advertisementDto.getDescription());
        advertisement.setUserPrice(advertisementDto.getUserPrice());
//        advertisement.setCategory(advertisementDto.getCategory()); ToDo: temporary, need to decide how to work with categories
        return advertisement;
    }

    @Override
    @Transactional
    public void updateToPaid(Long id) {
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Can't update the product (not found in the DB) id: " + id));
        advertisement.setIsPaid(true);
        advertisement.setExpirationDate(advertisement.getExpirationDate().plusDays(7)); // ToDo: days added should depend on the payment
    }
}
