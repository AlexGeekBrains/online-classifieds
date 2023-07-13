package com.geekbrains.onlineclassifieds.services;

import com.geekbrains.onlineclassifieds.entities.Category;

import java.util.Optional;

public interface CategoryService {
    Optional<Category> getCategoryById(Long categoryId);
    Optional<Category> getCategoryByName(String name);
}
