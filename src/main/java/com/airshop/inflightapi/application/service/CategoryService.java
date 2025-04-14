package com.airshop.inflightapi.application.service;

import java.util.List;
import com.airshop.inflightapi.application.port.in.CreateCategoryUseCase;
import com.airshop.inflightapi.application.port.in.GetCategoriesUseCase;
import com.airshop.inflightapi.application.port.out.CategoryRepository;
import com.airshop.inflightapi.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements CreateCategoryUseCase, GetCategoriesUseCase {

    private final CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + id));
    }

    public Category create(Category category) {
        return categoryRepository.save(category);
    }
}
