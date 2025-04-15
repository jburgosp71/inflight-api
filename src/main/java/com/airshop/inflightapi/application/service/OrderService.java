package com.airshop.inflightapi.application.service;

import com.airshop.inflightapi.application.port.in.*;
import com.airshop.inflightapi.application.port.out.OrderRepository;
import com.airshop.inflightapi.application.port.out.ProductRepository;
import com.airshop.inflightapi.domain.model.*;
import com.airshop.inflightapi.domain.model.enums.OrderStatus;
import com.airshop.inflightapi.domain.model.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements CreateOrderUseCase, UpdateOrderUseCase, GetOrderUseCase, FinalizeOrderUseCase {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProcessPaymentUseCase paymentProcessor;

    @Override
    public Order create(Order order) {
        if (order.getSeatLetter() == null ||
            order.getSeatLetter().isBlank() ||
            !order.getSeatLetter().matches("^[a-zA-Z]$")) {
            throw new IllegalArgumentException("Seat letter is required and must be a single letter (A-Z or a-z).");
        }

        if (order.getSeatNumber() <= 0) {
            throw new IllegalArgumentException("Seat number must be greater than 0.");
        }

        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    @Override
    public Order update(Long orderId, Order order) {
        Order existingOrder = orderRepository.findById(orderId);
        if (existingOrder == null) {
            throw new IllegalArgumentException("Order not found.");
        }

        if (order.getBuyerEmail() != null) {
            existingOrder.setBuyerEmail(order.getBuyerEmail());
        }

        double totalPrice = 0.0;
        for (OrderItem item : order.getItems()) {
            Product itemProduct = productRepository.findById(item.getProductId());
            boolean inStock = productRepository.checkStock(item.getProductId(), item.getQuantity());
            if (!inStock) {
                throw new IllegalArgumentException("Not enough stock for product " + item.getProductId());
            }
            totalPrice += item.getQuantity() * itemProduct.getPrice();
        }

        existingOrder.setItems(order.getItems());

        PaymentDetails paymentDetails = existingOrder.getPaymentDetails();
        if (paymentDetails == null) {
            paymentDetails = new PaymentDetails();
        }
        paymentDetails.setTotalPrice(totalPrice);
        paymentDetails.setStatus(PaymentStatus.PENDING);
        existingOrder.setPaymentDetails(paymentDetails);

        return orderRepository.save(existingOrder);
    }

    @Override
    public Order get(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public Order finalize(Long orderId, Order inputOrder) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found.");
        }

        if (order.getStatus() == OrderStatus.CONFIRMED) {
            throw new IllegalArgumentException("The order " + orderId + " has already been completed.");
        }

        PaymentDetails paymentInput = inputOrder.getPaymentDetails();
        if (paymentInput == null) {
            throw new IllegalArgumentException("Payment details are required.");
        }

        PaymentDetails processedDetails = paymentProcessor.process(paymentInput);
        order.setPaymentDetails(processedDetails);

        if (processedDetails.getStatus() == PaymentStatus.PAID) {
            order.setStatus(OrderStatus.CONFIRMED);
        } else if (processedDetails.getStatus() == PaymentStatus.FAILED ||
                   processedDetails.getStatus() == PaymentStatus.OFFLINE) {
            order.setStatus(OrderStatus.PENDING);
        }

        return orderRepository.save(order);
    }
}
