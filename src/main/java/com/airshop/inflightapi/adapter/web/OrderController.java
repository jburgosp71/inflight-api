package com.airshop.inflightapi.adapter.web;

import com.airshop.inflightapi.application.port.in.CreateOrderUseCase;
import com.airshop.inflightapi.application.port.in.UpdateOrderUseCase;
import com.airshop.inflightapi.application.port.in.GetOrderUseCase;
import com.airshop.inflightapi.application.port.in.FinalizeOrderUseCase;
import com.airshop.inflightapi.domain.model.Order;
import com.airshop.inflightapi.adapter.web.dto.OrderResponse;
import com.airshop.inflightapi.adapter.persistence.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final FinalizeOrderUseCase finalizeOrderUseCase;

    @PostMapping
    public OrderResponse createOrder(@RequestBody Order order) {
        Order createdOrder = createOrderUseCase.create(order);
        return OrderMapper.toResponse(createdOrder);
    }

    @PutMapping("/{orderId}")
    public OrderResponse updateOrder(@PathVariable Long orderId, @RequestBody Order order) {
        Order updatedOrder = updateOrderUseCase.update(orderId, order);
        return OrderMapper.toResponse(updatedOrder);
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable Long orderId) {
        Order order = getOrderUseCase.get(orderId);
        return OrderMapper.toResponse(order);
    }

    @PostMapping("/{orderId}/finalize")
    public OrderResponse finalizeOrder(@PathVariable Long orderId, @RequestBody Order order) {
        Order finalizedOrder = finalizeOrderUseCase.finalize(orderId, order);
        return OrderMapper.toResponse(finalizedOrder);
    }
}
