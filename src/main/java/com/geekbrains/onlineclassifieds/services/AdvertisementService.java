package com.geekbrains.onlineclassifieds.services;

import com.geekbrains.onlineclassifieds.dto.AdvertisementDto;
import com.geekbrains.onlineclassifieds.dto.AdvertisementDtoRes;
import com.geekbrains.onlineclassifieds.entities.Advertisement;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface AdvertisementService {
    Advertisement saveNewAdvertisement(AdvertisementDto advertisementDto, String username);

    Advertisement updateAdvertisementInfo(AdvertisementDto advertisementDto);

    void updateToPaid(Long id);

    Page<AdvertisementDtoRes> findAllWithFilter(BigDecimal minPrice, BigDecimal maxPrice, String partTitle, Long categoryId, Integer page);
}
