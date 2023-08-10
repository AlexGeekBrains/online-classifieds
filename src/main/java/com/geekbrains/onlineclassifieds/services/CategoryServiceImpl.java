package com.geekbrains.onlineclassifieds.services;

import com.geekbrains.onlineclassifieds.converters.CategoryConverter;
import com.geekbrains.onlineclassifieds.entities.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import com.geekbrains.onlineclassifieds.repositories.CategoryRepository;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;
    @Override
    public Optional<Category> getCategoryById(Long id){
        return categoryRepository.findById(id);
    }
    @Override
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List findAllCategory() {
    return categoryRepository.findAll().stream().map(categoryConverter::entityToDto).collect(Collectors.toList());
    }
}
