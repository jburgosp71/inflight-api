package com.airshop.inflightapi.application.port.out;

import com.airshop.inflightapi.domain.model.Order;

public interface OrderRepository {
    Order save(Order order);
    Order findById(Long orderId);
}
