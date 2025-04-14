package com.airshop.inflightapi.domain.model;

import com.airshop.inflightapi.domain.model.enums.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class PaymentDetails {

    private double totalPrice;
    private String cardToken;
    private PaymentStatus status;
    private LocalDateTime paymentDate;
    private String gateway;

    public static PaymentDetails empty() {
        return new PaymentDetails(0.0, null, null, null, null);
    }

    public PaymentDetails(double totalPrice, String cardToken, PaymentStatus status,
                          LocalDateTime paymentDate, String gateway) {
        this.totalPrice = totalPrice;
        this.cardToken = cardToken;
        this.status = status;
        this.paymentDate = paymentDate;
        this.gateway = gateway;
    }
}

