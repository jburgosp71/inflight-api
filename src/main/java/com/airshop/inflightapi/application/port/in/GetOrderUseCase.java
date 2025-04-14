package com.airshop.inflightapi.application.port.in;

import com.airshop.inflightapi.domain.model.Order;

public interface GetOrderUseCase {
    Order get(Long orderId);
}
