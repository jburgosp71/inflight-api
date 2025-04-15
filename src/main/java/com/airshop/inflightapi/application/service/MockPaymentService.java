package com.airshop.inflightapi.application.service;

import com.airshop.inflightapi.application.port.in.ProcessPaymentUseCase;
import com.airshop.inflightapi.domain.model.PaymentDetails;
import com.airshop.inflightapi.domain.model.enums.PaymentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class MockPaymentService implements ProcessPaymentUseCase {

    private final Random random;

    public MockPaymentService() {
        this.random = new Random();
    }

    public MockPaymentService(Random random) {
        this.random = random;
    }

    @Override
    public PaymentDetails process(PaymentDetails paymentDetails) {
        int randomValue = random.nextInt(100);

        paymentDetails.setPaymentDate(LocalDateTime.now());

        if (randomValue < 34) {
            paymentDetails.setStatus(PaymentStatus.PAID);
        } else if (randomValue < 67) {
            paymentDetails.setStatus(PaymentStatus.OFFLINE);
        } else {
            paymentDetails.setStatus(PaymentStatus.FAILED);
        }

        System.out.println("Mock payment result: " + paymentDetails.getStatus());

        return paymentDetails;
    }
}
