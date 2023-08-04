package com.geekbrains.onlineclassifieds.services;

import com.geekbrains.onlineclassifieds.dto.CategoryDto;
import com.geekbrains.onlineclassifieds.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<Category> getCategoryById(Long categoryId);
    Optional<Category> getCategoryByName(String name);

    List<CategoryDto> findAllCategory();
}