package com.geekbrains.onlineclassifieds.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleError {
    @Schema(description = "HTTP status code", example = "401")
    private int statusCode;
    @Schema(description = "Error message", example = "error message")
    private String message;
}
