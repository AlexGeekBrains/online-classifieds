package com.geekbrains.onlineclassifieds.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "Advertisement title", example = "iPhone X")
    private String title;
    @Schema(description = "Advertisement description", example = "Brand new iPhone X")
    private String description;
    @Schema(description = "User price for the advertisement", example = "250.00")
    private BigDecimal userPrice;
    @Schema(description = "Flag indicating whether the advertisement is paid", example = "true")
    private Boolean isPaid;
    @Schema(description = "Flag indicating whether the advertisement is deleted", example = "false")
    private Boolean isDeleted;
    @Schema(description = "Expiration date of the advertisement")
    private LocalDateTime expirationDate;
    @Schema(description = "Category information for the advertisement")
    private CategoryDto categoryDto; // ToDo: Need to talk about it (-> new / update Adv)
}