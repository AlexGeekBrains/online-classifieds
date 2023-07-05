package com.geekbrains.onlineclassifieds.converters;

import com.geekbrains.onlineclassifieds.dto.AdvertisementDto;
import com.geekbrains.onlineclassifieds.dto.AdvertisementDtoRes;
import com.geekbrains.onlineclassifieds.entities.Advertisement;
import com.geekbrains.onlineclassifieds.entities.User;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class AdvertisementConverter {
    public Advertisement dtoToEntity(AdvertisementDto advertisementDto, User user) {
        return new Advertisement(
                advertisementDto.getTitle(),
                advertisementDto.getDescription(),
                advertisementDto.getUserPrice(),
                advertisementDto.getIsPaid(),
                advertisementDto.getIsDeleted(),
                advertisementDto.getExpirationDate(),
                user
             //   advertisementDto.getCategory() ToDo: temporary, need to decide how to work with categories
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
                advertisement.getPayments(),
                advertisement.getCategory(),
                advertisement.getUser()
        );
    }

    public AdvertisementDtoRes entityToDtoRes(Advertisement advertisement) {
        Hibernate.initialize(advertisement.getCategory());
        return new AdvertisementDtoRes(
                advertisement.getId(),
                advertisement.getTitle(),
                advertisement.getDescription(),
                advertisement.getUserPrice(),
                advertisement.getIsPaid(),
                advertisement.getExpirationDate(),
                advertisement.getUser().getUsername(),
                advertisement.getCategory().getName(),
                advertisement.getCategory().getId()
        );
    }
}
