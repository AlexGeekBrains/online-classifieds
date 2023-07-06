package com.geekbrains.onlineclassifieds.services;

import com.geekbrains.onlineclassifieds.entities.Category;
import com.geekbrains.onlineclassifieds.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public Optional<Category> getCategoryById(Long id){
        return categoryRepository.findById(id);
    }
}
