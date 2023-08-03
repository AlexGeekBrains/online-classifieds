package com.geekbrains.onlineclassifieds.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDto<T> {
    @ArraySchema(schema = @Schema(implementation = AdvertisementDto.class))
    private List<T> content;
    @Schema(description = "Page number", example = "0")
    private int number;
    @Schema(description = "Page size", example = "10")
    private int size;
    @Schema(description = "Total elements", example = "20")
    private long totalElements;
    @Schema(description = "Total pages", example = "2")
    private int totalPages;
    @Schema(description = "Last page", example = "false")
    private boolean last;
    @Schema(description = "First page", example = "true")
    private boolean first;
    @Schema(description = "Empty page", example = "false")
    private boolean empty;
}
