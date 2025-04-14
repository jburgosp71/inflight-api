package com.airshop.inflightapi.application.port.in;

import com.airshop.inflightapi.domain.model.Order;

public interface UpdateOrderUseCase {
    Order update(Long orderId, Order order);
}
