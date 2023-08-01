package com.geekbrains.onlineclassifieds.dto;

import java.math.BigDecimal;

public record AdvertisementInfoDto (String title, String description, BigDecimal userPrice, String categoryName) {}
