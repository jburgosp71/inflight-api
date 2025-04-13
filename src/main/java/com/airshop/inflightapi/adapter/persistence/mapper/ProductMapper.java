package com.airshop.inflightapi.adapter.persistence.mapper;

import com.airshop.inflightapi.adapter.persistence.entity.ProductEntity;
import com.airshop.inflightapi.domain.model.Product;

public class ProductMapper {

    public static Product toDomain(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getImageUrl(),
                entity.getStock(),
                CategoryMapper.toDomain(entity.getCategory())
        );
    }

    public static ProductEntity toEntity(Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setImageUrl(product.getImageUrl());
        entity.setStock(product.getStock());
        entity.setCategory(CategoryMapper.toEntity(product.getCategory()));
        return entity;
    }
}
