package com.geekbrains.onlineclassifieds.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDtoRes {
    private Long id;

    private String title;

    private String description;

    private BigDecimal userPrice;

    private Boolean isPaid;

    private LocalDateTime expirationDate;

    private String userName;

    private String categoryName;

    private Long categoryId;
}
