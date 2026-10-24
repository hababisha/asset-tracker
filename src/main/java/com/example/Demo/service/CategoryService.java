package com.example.Demo.service;

import com.example.Demo.dto.CategoryRequest;
import com.example.Demo.dto.CategoryResponse;
import com.example.Demo.entity.Category;
import com.example.Demo.exception.BadRequestException;
import com.example.Demo.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName()))
                .toList();
    }

    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        String name = request.getName().trim();
        if (categoryRepository.existsByName(name)) {
            throw new BadRequestException("Category already exists: " + name);
        }
        Category saved = categoryRepository.save(new Category(name));
        return new CategoryResponse(saved.getId(), saved.getName());
    }
}
