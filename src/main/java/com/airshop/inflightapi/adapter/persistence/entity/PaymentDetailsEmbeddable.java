package com.airshop.inflightapi.adapter.persistence.entity;

import com.airshop.inflightapi.domain.model.enums.PaymentStatus;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Embeddable
@Getter @Setter
@NoArgsConstructor
public class PaymentDetailsEmbeddable {

    private double totalPrice;
    private String cardToken;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDateTime paymentDate;
    private String gateway;
}
