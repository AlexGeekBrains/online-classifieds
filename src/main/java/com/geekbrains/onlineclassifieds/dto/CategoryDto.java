package com.geekbrains.onlineclassifieds.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "Category name", example = "Electronics")
    private String name;
}
