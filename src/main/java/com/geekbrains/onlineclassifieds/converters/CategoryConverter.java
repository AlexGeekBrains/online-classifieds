package com.geekbrains.onlineclassifieds.converters;

import com.geekbrains.onlineclassifieds.dto.CategoryDto;
import com.geekbrains.onlineclassifieds.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    public CategoryDto entityToDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }
}