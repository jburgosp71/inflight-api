package com.airshop.inflightapi.adapter.persistence;

import com.airshop.inflightapi.adapter.persistence.entity.OrderEntity;
import com.airshop.inflightapi.adapter.persistence.entity.OrderItemEntity;
import com.airshop.inflightapi.adapter.persistence.entity.ProductEntity;
import com.airshop.inflightapi.adapter.persistence.mapper.OrderItemMapper;
import com.airshop.inflightapi.adapter.persistence.mapper.OrderMapper;
import com.airshop.inflightapi.adapter.persistence.repository.SpringDataOrderRepository;
import com.airshop.inflightapi.adapter.persistence.repository.SpringDataProductRepository;
import com.airshop.inflightapi.application.port.out.OrderRepository;
import com.airshop.inflightapi.domain.model.Order;
import com.airshop.inflightapi.domain.model.OrderItem;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaOrderRepositoryAdapter implements OrderRepository {

    private final SpringDataOrderRepository jpaOrderRepository;
    private final SpringDataProductRepository jpaProductRepository;

    @Override
    public Order save(Order order) {
        List<OrderItemEntity> itemEntities = new ArrayList<>();
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            for (OrderItem item : order.getItems()) {
                ProductEntity productEntity = jpaProductRepository.findById(item.getProductId())
                        .orElseThrow(() -> new IllegalArgumentException("Product not found: " + item.getProductId()));
                itemEntities.add(OrderItemMapper.toEntity(item, productEntity));
            }
        }

        OrderEntity entity = OrderMapper.toEntity(order, itemEntities);
        OrderEntity saved = jpaOrderRepository.save(entity);

        return OrderMapper.toDomain(saved);
    }

    @Override
    public Order findById(Long orderId) {
        return jpaOrderRepository.findById(orderId)
                .map(OrderMapper::toDomain)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));
    }
}
