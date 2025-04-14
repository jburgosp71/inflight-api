package com.airshop.inflightapi.adapter.persistence.mapper;

import com.airshop.inflightapi.adapter.persistence.entity.OrderItemEntity;
import com.airshop.inflightapi.adapter.persistence.entity.ProductEntity;
import com.airshop.inflightapi.domain.model.OrderItem;

public class OrderItemMapper {

    public static OrderItem toDomain(OrderItemEntity entity) {
        return new OrderItem(
                entity.getId(),
                entity.getProduct().getId(),
                entity.getQuantity()
        );
    }

    public static OrderItemEntity toEntity(OrderItem item, ProductEntity productEntity) {
        OrderItemEntity entity = new OrderItemEntity();
        entity.setId(item.getId());
        entity.setProduct(productEntity);
        entity.setQuantity(item.getQuantity());
        return entity;
    }
}
