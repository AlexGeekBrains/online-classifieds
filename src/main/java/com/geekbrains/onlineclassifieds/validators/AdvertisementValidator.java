package com.geekbrains.onlineclassifieds.validators;

import com.geekbrains.onlineclassifieds.dto.AdvertisementDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdvertisementValidator {
    public void validate(AdvertisementDto advertisementDto) {
        List<String> errors = new ArrayList<>();
        if (advertisementDto.getPrice() != null) {
            if (advertisementDto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                errors.add("Advertisement price can't be zero or lower");
            }
        }
        if (advertisementDto.getTitle() == null || advertisementDto.getTitle().isBlank()) {
            errors.add("Advertisement's title can't be blank");
        }
        if (advertisementDto.getDescription() == null || advertisementDto.getDescription().isBlank()) {
            errors.add("Advertisement's description can't be blank");
        }
        if (advertisementDto.getCategory() == null) {
            errors.add("Advertisement's category can't be blank");
        }
        if (!errors.isEmpty()) {
            System.out.println(errors); // @ToDo: errors log
            throw new IllegalArgumentException(String.join(", ", errors));
        }
    }
}
