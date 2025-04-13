package com.airshop.inflightapi.domain.model;

import com.airshop.inflightapi.domain.model.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class Order {

    private Long id;
    private List<OrderItem> items;
    private PaymentDetails paymentDetails;
    private String buyerEmail;
    private String seatLetter;
    private int seatNumber;
    private OrderStatus status;

    public Order(Long id, List<OrderItem> items, PaymentDetails paymentDetails,
                 String buyerEmail, String seatLetter, int seatNumber, OrderStatus status) {
        this.id = id;
        this.items = items;
        this.paymentDetails = paymentDetails;
        this.buyerEmail = buyerEmail;
        this.seatLetter = seatLetter;
        this.seatNumber = seatNumber;
        this.status = status;
    }
}

