package com.airshop.inflightapi.application.port.in;

import com.airshop.inflightapi.domain.model.PaymentDetails;

public interface ProcessPaymentUseCase {
    PaymentDetails process(PaymentDetails paymentDetails);
}
