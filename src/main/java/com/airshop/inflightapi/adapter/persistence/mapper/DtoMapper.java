package com.airshop.inflightapi.adapter.persistence.mapper;

import com.airshop.inflightapi.adapter.web.dto.*;
import com.airshop.inflightapi.domain.model.Category;
import com.airshop.inflightapi.domain.model.Product;

public class DtoMapper {

    public static CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getParentCategory() != null ? category.getParentCategory().getId() : null
        );
    }

    public static Category toDomain(CategoryRequest request, Category parentCategory) {
        Category category = new Category();
        category.setName(request.getName());
        category.setParentCategory(parentCategory);

        return category;
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                product.getStock(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getCategory() != null ? product.getCategory().getName() : null
        );
    }

    public static Product toDomain(ProductRequest request, Category category) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);
        return product;
    }
}