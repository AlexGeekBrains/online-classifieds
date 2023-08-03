package com.geekbrains.onlineclassifieds.services;

import com.geekbrains.onlineclassifieds.dto.AdvertisementDto;
import com.geekbrains.onlineclassifieds.dto.AdvertisementInfoDto;
import com.geekbrains.onlineclassifieds.dto.PageResponseDto;
import com.geekbrains.onlineclassifieds.dto.UserContactsDto;
import com.geekbrains.onlineclassifieds.entities.Advertisement;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface AdvertisementService {
    Advertisement saveNewAdvertisement(AdvertisementDto advertisementDto, String username);

    Advertisement updateAdvertisementInfo(Long id, AdvertisementDto advertisementDto, String username);

    void updateToPaid(Long id);

    PageResponseDto<AdvertisementDto> findAllWithFilter(BigDecimal minPrice, BigDecimal maxPrice, String partTitle, Long categoryId, Integer page, Boolean isNotDeleted, Boolean isNotExpiredYet);

    void updateExpiredAdvertisements(LocalDateTime currentDateTime, int pageSize);

    void markAdvertisementAsDeleted(Long advertisementId, String username);

    AdvertisementInfoDto showDetailedInfo(Long id);

    UserContactsDto showUserContacts(Long id);
}