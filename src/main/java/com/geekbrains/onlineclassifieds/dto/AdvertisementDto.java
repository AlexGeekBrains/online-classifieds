package com.geekbrains.onlineclassifieds.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDto {

    private Long id;

    private String title;

    private String description;

    private BigDecimal userPrice;

    private Boolean isPaid;

    private Boolean isDeleted;

    private LocalDateTime expirationDate;

    private CategoryDto categoryDto; // ToDo: Need to talk about it (-> new / update Adv)
}
