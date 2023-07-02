package com.geekbrains.onlineclassifieds.converters;

import com.geekbrains.onlineclassifieds.dto.AdvertisementDto;
import com.geekbrains.onlineclassifieds.entities.Advertisement;
import com.geekbrains.onlineclassifieds.entities.User;
import org.springframework.stereotype.Component;

@Component
public class AdvertisementConverter {
    public Advertisement dtoToEntity(AdvertisementDto advertisementDto, User user) {
        return new Advertisement(
                advertisementDto.getTitle(),
                advertisementDto.getDescription(),
                advertisementDto.getIsPaid(),
                advertisementDto.getIsDeleted(),
                advertisementDto.getExpirationDate(),
                user,
                advertisementDto.getCategory()
        );
    }

    public AdvertisementDto entityToDto(Advertisement advertisement) {
        return new AdvertisementDto(
                advertisement.getId(),
                advertisement.getTitle(),
                advertisement.getDescription(),
                advertisement.getPrice(),
                advertisement.getIsPaid(),
                advertisement.getIsDeleted(),
                advertisement.getExpirationDate(),
                advertisement.getPayments(),
                advertisement.getCategory()
        );
    }
}
