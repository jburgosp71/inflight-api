package com.airshop.inflightapi.adapter.persistence.mapper;

import com.airshop.inflightapi.adapter.persistence.entity.OrderItemEntity;
import com.airshop.inflightapi.domain.model.OrderItem;

public class OrderItemMapper {

    public static OrderItem toDomain(OrderItemEntity entity) {
        return new OrderItem(
                entity.getId(),
                ProductMapper.toDomain(entity.getProduct()),
                entity.getQuantity()
        );
    }

    public static OrderItemEntity toEntity(OrderItem item) {
        OrderItemEntity entity = new OrderItemEntity();
        entity.setId(item.getId());
        entity.setProduct(ProductMapper.toEntity(item.getProduct()));
        entity.setQuantity(item.getQuantity());
        return entity;
    }
}
