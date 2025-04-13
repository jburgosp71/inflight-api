package com.airshop.inflightapi.adapter.persistence.mapper;

import com.airshop.inflightapi.domain.model.Category;
import com.airshop.inflightapi.adapter.persistence.entity.CategoryEntity;

public class CategoryMapper {
    public static Category toDomain(CategoryEntity entity) {
        return new Category(
                entity.getId(),
                entity.getName(),
                entity.getParentCategory() != null ? toDomain(entity.getParentCategory()) : null);
    }

    public static CategoryEntity toEntity(Category category) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(category.getId());
        entity.setName(category.getName());
        if (category.getParentCategory() != null) {
            entity.setParentCategory(toEntity(category.getParentCategory()));
        }
        return entity;
    }
}
