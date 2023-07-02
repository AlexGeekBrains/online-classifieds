package com.geekbrains.onlineclassifieds.dto;

import com.geekbrains.onlineclassifieds.entities.Category;
import com.geekbrains.onlineclassifieds.entities.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDto {

    private Long id;

    private String title;

    private String description;

    private BigDecimal price;

    private Boolean isPaid;

    private Boolean isDeleted;

    private LocalDateTime expirationDate;

    private Collection<Payment> payments;

    private Category category;
}
