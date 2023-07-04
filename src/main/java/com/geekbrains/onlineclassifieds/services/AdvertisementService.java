package com.geekbrains.onlineclassifieds.services;

import com.geekbrains.onlineclassifieds.dto.AdvertisementDto;
import com.geekbrains.onlineclassifieds.entities.Advertisement;

public interface AdvertisementService {
    Advertisement saveNewAdvertisement(AdvertisementDto advertisementDto, String username);

    Advertisement updateAdvertisementInfo(AdvertisementDto advertisementDto);

    void updateToPaid(Long id);
}
