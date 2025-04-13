package com.airshop.inflightapi.adapter.persistence.mapper;

import com.airshop.inflightapi.adapter.persistence.entity.*;
import com.airshop.inflightapi.domain.model.*;

import java.util.stream.Collectors;

public class OrderMapper {

    public static Order toDomain(OrderEntity entity) {
        return new Order(
                entity.getId(),
                entity.getItems().stream()
                        .map(OrderItemMapper::toDomain)
                        .collect(Collectors.toList()),
                PaymentDetailsMapper.toDomain(entity.getPaymentDetails()),
                entity.getBuyerEmail(),
                entity.getSeatLetter(),
                entity.getSeatNumber(),
                entity.getStatus()
        );
    }

    public static OrderEntity toEntity(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setItems(order.getItems().stream()
                .map(OrderItemMapper::toEntity)
                .collect(Collectors.toList()));
        entity.setPaymentDetails(PaymentDetailsMapper.toEntity(order.getPaymentDetails()));
        entity.setBuyerEmail(order.getBuyerEmail());
        entity.setSeatLetter(order.getSeatLetter());
        entity.setSeatNumber(order.getSeatNumber());
        entity.setStatus(order.getStatus());
        return entity;
    }
}
