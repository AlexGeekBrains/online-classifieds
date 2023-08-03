package com.geekbrains.onlineclassifieds.validators;

import com.geekbrains.onlineclassifieds.exceptions.FieldsValidationException;
import com.geekbrains.onlineclassifieds.dto.AdvertisementDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdvertisementValidator {
    public void validate(AdvertisementDto advertisementDto) {
        List<String> errors = new ArrayList<>();
        if (advertisementDto.getUserPrice() == null) {
            errors.add("Advertisement price can't be blank");
        } else if (advertisementDto.getUserPrice().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Advertisement price can't be zero or lower");
        }
        if (advertisementDto.getTitle() == null || advertisementDto.getTitle().isBlank()) {
            errors.add("Advertisement's title can't be blank");
        }
        if (advertisementDto.getDescription() == null || advertisementDto.getDescription().isBlank()) {
            errors.add("Advertisement's description can't be blank");
        }
        if (advertisementDto.getCategoryDto() == null) {
            errors.add("Advertisement's category can't be blank");
        } else if (advertisementDto.getCategoryDto().getName() == null) {
            errors.add("Inconsistent category - no name specified");
        }
        if (!errors.isEmpty()) {
            throw new FieldsValidationException(errors);
        }
    }
}
