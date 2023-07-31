package com.geekbrains.onlineclassifieds.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListError {
    @Schema(description = "HTTP status code", example = "401")
    private int statusCode;
    @Schema(description = "List of error messages", example = "[\"error message\", \"error message\"]")
    private List<String> messages;
}
