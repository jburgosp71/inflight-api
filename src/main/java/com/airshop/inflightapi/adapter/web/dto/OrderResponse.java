package com.airshop.inflightapi.adapter.web.dto;

import com.airshop.inflightapi.domain.model.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderResponse {

    private Long id;
    private String buyerEmail;
    private String seatLetter;
    private int seatNumber;
    private OrderStatus status;
    private double totalPrice;

    public OrderResponse(Long id, String buyerEmail, String seatLetter, int seatNumber, OrderStatus status, double totalPrice) {
        this.id = id;
        this.buyerEmail = buyerEmail;
        this.seatLetter = seatLetter;
        this.seatNumber = seatNumber;
        this.status = status;
        this.totalPrice = totalPrice;
    }

}
