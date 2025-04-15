package com.airshop.inflightapi.application.service;

import com.airshop.inflightapi.application.port.in.ProcessPaymentUseCase;
import com.airshop.inflightapi.application.port.out.OrderRepository;
import com.airshop.inflightapi.application.port.out.ProductRepository;
import com.airshop.inflightapi.domain.model.*;
import com.airshop.inflightapi.domain.model.enums.OrderStatus;
import com.airshop.inflightapi.domain.model.enums.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProcessPaymentUseCase paymentProcessor;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testCreateShouldSetPendingStatusAndSaveOrder() {
        Order order = new Order();
        order.setSeatLetter("A");
        order.setSeatNumber(12);

        Order savedOrder = new Order();
        savedOrder.setStatus(OrderStatus.PENDING);

        Mockito.when(orderRepository.save(order)).thenReturn(savedOrder);

        Order result = orderService.create(order);

        assertEquals(OrderStatus.PENDING, result.getStatus());
        Mockito.verify(orderRepository).save(order);
    }

    @Test
    void testCreateShouldThrow_WhenSeatLetterInvalid() {
        Order order = new Order();
        order.setSeatLetter("AB");  // inválido
        order.setSeatNumber(10);

        assertThrows(IllegalArgumentException.class, () -> orderService.create(order));
    }

    @Test
    void testCreateShouldThrow_WhenSeatNumberInvalid() {
        Order order = new Order();
        order.setSeatLetter("A");
        order.setSeatNumber(0);

        assertThrows(IllegalArgumentException.class, () -> orderService.create(order));
    }

    @Test
    void testGetShouldReturnOrder_WhenFound() {
        Long orderId = 1L;
        Order order = new Order();
        Mockito.when(orderRepository.findById(orderId)).thenReturn(order);

        Order result = orderService.get(orderId);

        assertEquals(order, result);
    }

    @Test
    void testUpdateShouldUpdateItemsAndRecalculateTotal() {
        Long orderId = 1L;

        OrderItem item = new OrderItem(null, 100L, 2); // productId = 100
        Order inputOrder = new Order();
        inputOrder.setItems(List.of(item));

        Product product = createTestProduct(100L,10.0);
        Order existingOrder = new Order();
        existingOrder.setPaymentDetails(null);

        Mockito.when(orderRepository.findById(orderId)).thenReturn(existingOrder);
        Mockito.when(productRepository.findById(100L)).thenReturn(product);
        Mockito.when(productRepository.checkStock(100L, 2)).thenReturn(true);
        Mockito.when(orderRepository.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        Order result = orderService.update(orderId, inputOrder);

        assertEquals(20.0, result.getPaymentDetails().getTotalPrice());
        assertEquals(PaymentStatus.PENDING, result.getPaymentDetails().getStatus());
        assertEquals(inputOrder.getItems(), result.getItems());
    }

    @Test
    void testUpdateShouldThrow_WhenProductOutOfStock() {
        Long orderId = 1L;

        OrderItem item = new OrderItem(null, 100L, 5);
        Order inputOrder = new Order();
        inputOrder.setItems(List.of(item));

        Product product = createTestProduct(100L,10.0);
        Order existingOrder = new Order();

        Mockito.when(orderRepository.findById(orderId)).thenReturn(existingOrder);
        Mockito.when(productRepository.findById(100L)).thenReturn(product);
        Mockito.when(productRepository.checkStock(100L, 5)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> orderService.update(orderId, inputOrder));
    }

    @Test
    void testFinalizeShouldConfirmOrder_WhenPaymentIsPaid() {
        Long orderId = 1L;
        PaymentDetails inputDetails = new PaymentDetails();
        inputDetails.setCardToken("123");
        inputDetails.setGateway("VISA");

        PaymentDetails processedDetails = new PaymentDetails();
        processedDetails.setStatus(PaymentStatus.PAID);
        processedDetails.setTotalPrice(100.0);

        Order existingOrder = new Order();
        existingOrder.setStatus(OrderStatus.PENDING);

        Order inputOrder = new Order();
        inputOrder.setPaymentDetails(inputDetails);

        Mockito.when(orderRepository.findById(orderId)).thenReturn(existingOrder);
        Mockito.when(paymentProcessor.process(inputDetails)).thenReturn(processedDetails);
        Mockito.when(orderRepository.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        Order result = orderService.finalize(orderId, inputOrder);

        assertEquals(OrderStatus.CONFIRMED, result.getStatus());
        assertEquals(processedDetails, result.getPaymentDetails());
    }

    @Test
    void testFinalizeShouldRemainPending_WhenPaymentFailed() {
        Long orderId = 1L;

        PaymentDetails inputDetails = new PaymentDetails();
        PaymentDetails processedDetails = new PaymentDetails();
        processedDetails.setStatus(PaymentStatus.FAILED);

        Order existingOrder = new Order();
        existingOrder.setStatus(OrderStatus.PENDING);

        Order inputOrder = new Order();
        inputOrder.setPaymentDetails(inputDetails);

        Mockito.when(orderRepository.findById(orderId)).thenReturn(existingOrder);
        Mockito.when(paymentProcessor.process(inputDetails)).thenReturn(processedDetails);
        Mockito.when(orderRepository.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        Order result = orderService.finalize(orderId, inputOrder);

        assertEquals(OrderStatus.PENDING, result.getStatus());
    }

    @Test
    void testFinalizeShouldThrow_WhenOrderAlreadyConfirmed() {
        Long orderId = 1L;
        Order existingOrder = new Order();
        existingOrder.setStatus(OrderStatus.CONFIRMED);

        Mockito.when(orderRepository.findById(orderId)).thenReturn(existingOrder);

        Order inputOrder = new Order();
        inputOrder.setPaymentDetails(new PaymentDetails());

        assertThrows(IllegalArgumentException.class, () -> orderService.finalize(orderId, inputOrder));
    }

    private static Product createTestProduct(Long id, double price) {
        Product p = new Product();
        p.setId(id);
        p.setName("Test Product");
        p.setPrice(price);
        p.setStock(100);
        p.setCategory(new Category(1L, "Test Category", null));
        return p;
    }
}
