package com.airshop.inflightapi.application.port.in;


import com.airshop.inflightapi.domain.model.Order;

public interface FinalizeOrderUseCase {
    Order finalize(Long orderId, Order order);
}
