package com.geekbrains.onlineclassifieds.services;

import com.geekbrains.onlineclassifieds.dto.AdvertisementDto;
import com.geekbrains.onlineclassifieds.entities.Advertisement;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface AdvertisementService {
    Advertisement saveNewAdvertisement(AdvertisementDto advertisementDto, String username);

    Advertisement updateAdvertisementInfo(Long id, AdvertisementDto advertisementDto);

    void updateToPaid(Long id);

    Page<AdvertisementDto> findAllWithFilter(BigDecimal minPrice, BigDecimal maxPrice, String partTitle, Long categoryId, Integer page, Boolean isNotDeleted, Boolean isNotExpiredYet);

    void updateExpiredAdvertisements(LocalDateTime currentDateTime, int pageSize);
}