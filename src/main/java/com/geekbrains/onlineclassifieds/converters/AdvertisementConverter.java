package com.geekbrains.onlineclassifieds.converters;

import com.geekbrains.onlineclassifieds.dto.AdvertisementDto;
import com.geekbrains.onlineclassifieds.entities.Advertisement;
import com.geekbrains.onlineclassifieds.entities.Category;
import com.geekbrains.onlineclassifieds.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AdvertisementConverter {
    private final CategoryConverter categoryConverter;

    public Advertisement dtoToEntity(AdvertisementDto advertisementDto, User user, Category category) {
        return new Advertisement(
                advertisementDto.getTitle(),
                advertisementDto.getDescription(),
                advertisementDto.getUserPrice(),
                advertisementDto.getIsPaid(),
                advertisementDto.getIsDeleted(),
                advertisementDto.getExpirationDate(),
                user,
                category
        );
    }

    public AdvertisementDto entityToDto(Advertisement advertisement) {
        return new AdvertisementDto(
                advertisement.getId(),
                advertisement.getTitle(),
                advertisement.getDescription(),
                advertisement.getUserPrice(),
                advertisement.getIsPaid(),
                advertisement.getIsDeleted(),
                advertisement.getExpirationDate(),
                categoryConverter.entityToDto(advertisement.getCategory())
        );
    }
}
