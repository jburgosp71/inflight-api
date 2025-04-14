package com.airshop.inflightapi.adapter.persistence.mapper;

import com.airshop.inflightapi.adapter.persistence.entity.*;
import com.airshop.inflightapi.adapter.web.dto.OrderResponse;
import com.airshop.inflightapi.domain.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static Order toDomain(OrderEntity entity) {
        List<OrderItem> items = entity.getItems() != null
                ? entity.getItems().stream()
                .map(OrderItemMapper::toDomain)
                .collect(Collectors.toList())
                : new ArrayList<>();

        PaymentDetails paymentDetails = PaymentDetailsMapper.toDomain(entity.getPaymentDetails());

        return new Order(
                entity.getId(),
                items,
                paymentDetails,
                entity.getBuyerEmail(),
                entity.getSeatLetter(),
                entity.getSeatNumber(),
                entity.getStatus()
        );
    }

    public static OrderEntity toEntity(Order order, List<OrderItemEntity> itemEntities) {
        OrderEntity entity = new OrderEntity();
        entity.setItems(itemEntities);

        entity.setId(order.getId());
        entity.setItems(itemEntities);
        entity.setPaymentDetails(PaymentDetailsMapper.toEntity(order.getPaymentDetails()));
        entity.setBuyerEmail(order.getBuyerEmail());
        entity.setSeatLetter(order.getSeatLetter());
        entity.setSeatNumber(order.getSeatNumber());
        entity.setStatus(order.getStatus());
        return entity;
    }

    public static OrderResponse toResponse(Order order) {
        double totalPrice = order.getPaymentDetails() != null
                ? order.getPaymentDetails().getTotalPrice()
                : 0;

        return new OrderResponse(
                order.getId(),
                order.getBuyerEmail(),
                order.getSeatLetter(),
                order.getSeatNumber(),
                order.getStatus(),
                totalPrice
        );
    }
}
