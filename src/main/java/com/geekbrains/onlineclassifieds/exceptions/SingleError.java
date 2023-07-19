package com.geekbrains.onlineclassifieds.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleError {
    private int statusCode;
    private String message;
}
