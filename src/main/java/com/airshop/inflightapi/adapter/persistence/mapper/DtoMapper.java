package com.airshop.inflightapi.adapter.persistence.mapper;

import com.airshop.inflightapi.adapter.web.dto.CategoryResponse;
import com.airshop.inflightapi.adapter.web.dto.ProductResponse;
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
}