package com.airshop.inflightapi.application.service;

import com.airshop.inflightapi.application.port.out.CategoryRepository;
import com.airshop.inflightapi.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void testShouldReturnAllCategories() {
        Category drinks = new Category(1L, "Drinks", null);
        Category snacks = new Category(2L, "Snacks", null);
        when(categoryRepository.findAll()).thenReturn(List.of(drinks, snacks));

        List<Category> result = categoryService.getAll();

        assertThat(result).containsExactly(drinks, snacks);
        verify(categoryRepository).findAll();
    }

    @Test
    void testShouldCreateCategory() {
        Category input = new Category(null, "New", null);
        Category saved = new Category(99L, "New", null);
        when(categoryRepository.save(input)).thenReturn(saved);

        Category result = categoryService.create(input);

        assertThat(result).isEqualTo(saved);
        verify(categoryRepository).save(input);
    }
}
