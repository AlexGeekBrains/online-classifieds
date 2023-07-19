package com.geekbrains.onlineclassifieds.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListError {
    private int statusCode;
    private List<String> messages;
}
