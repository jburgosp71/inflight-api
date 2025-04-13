package com.airshop.inflightapi.application.service;

import com.airshop.inflightapi.application.port.out.ProductRepository;
import com.airshop.inflightapi.domain.model.Product;
import com.airshop.inflightapi.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    void shouldReturnAllProductsWhenCategoryIdIsNull() {
        Category drinks = new Category(1L, "Drinks", null);
        Category snacks = new Category(2L, "Snacks", null);

        Product soda = new Product(1L, "Soda", 2.5, "http://example.com/soda.jpg", 100, drinks);
        Product chips = new Product(2L, "Chips", 1.5, "http://example.com/chips.jpg", 50, snacks);

        when(productRepository.findAll(null)).thenReturn(List.of(soda, chips));

        List<Product> result = productService.getAll(null);

        assertThat(result).containsExactly(soda, chips);
        verify(productRepository).findAll(null);
    }

    @Test
    void shouldReturnProductsByCategoryId() {
        Category drinks = new Category(1L, "Drinks", null);

        Product soda = new Product(1L, "Soda", 2.5, "http://example.com/soda.jpg", 100, drinks);

        when(productRepository.findAll(1L)).thenReturn(List.of(soda));

        List<Product> result = productService.getAll(1L);

        assertThat(result).containsExactly(soda);
        verify(productRepository).findAll(1L);
    }

    @Test
    void shouldCreateProduct() {
        Category drinks = new Category(1L, "Drinks", null);
        Product input = new Product(null, "Juice", 3.0, "http://example.com/juice.jpg", 200, drinks);
        Product saved = new Product(99L, "Juice", 3.0, "http://example.com/juice.jpg", 200, drinks);

        when(productRepository.save(input)).thenReturn(saved);

        Product result = productService.create(input);

        assertThat(result).isEqualTo(saved);
        verify(productRepository).save(input);
    }
}
