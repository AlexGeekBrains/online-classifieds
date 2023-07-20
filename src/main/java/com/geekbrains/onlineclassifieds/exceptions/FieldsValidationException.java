package com.geekbrains.onlineclassifieds.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FieldsValidationException extends RuntimeException {
    private List<String> errorFieldsMessages;
    public FieldsValidationException(List<String> errorFieldsMessages) {
        super(String.join(", ", errorFieldsMessages));
        this.errorFieldsMessages = errorFieldsMessages;
    }
}