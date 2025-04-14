package com.airshop.inflightapi.application.port.out;

import com.airshop.inflightapi.domain.model.PaymentDetails;

public interface PaymentRepository {
    void savePaymentDetails(PaymentDetails paymentDetails);
}