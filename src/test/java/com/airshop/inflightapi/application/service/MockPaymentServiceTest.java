package com.airshop.inflightapi.application.service;

import com.airshop.inflightapi.domain.model.PaymentDetails;
import com.airshop.inflightapi.domain.model.enums.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MockPaymentServiceTest {
    @Test
    public void testProcessReturnsPaidWhenRandomLessThan34() {
        Random mockRandom = Mockito.mock(Random.class);
        Mockito.when(mockRandom.nextInt(100)).thenReturn(30);

        MockPaymentService service = new MockPaymentService(mockRandom);
        PaymentDetails input = new PaymentDetails();

        PaymentDetails result = service.process(input);

        assertEquals(PaymentStatus.PAID, result.getStatus());
        assertNotNull(result.getPaymentDate());
    }

    @Test
    public void testProcessReturnsOfflineWhenRandomBetween34And66() {
        Random mockRandom = Mockito.mock(Random.class);
        Mockito.when(mockRandom.nextInt(100)).thenReturn(60);

        MockPaymentService service = new MockPaymentService(mockRandom);
        PaymentDetails input = new PaymentDetails();

        PaymentDetails result = service.process(input);

        assertEquals(PaymentStatus.OFFLINE, result.getStatus());
        assertNotNull(result.getPaymentDate());
    }

    @Test
    public void testProcessReturnsFailedWhenRandom67OrMore() {
        Random mockRandom = Mockito.mock(Random.class);
        Mockito.when(mockRandom.nextInt(100)).thenReturn(90);

        MockPaymentService service = new MockPaymentService(mockRandom);
        PaymentDetails input = new PaymentDetails();

        PaymentDetails result = service.process(input);

        assertEquals(PaymentStatus.FAILED, result.getStatus());
        assertNotNull(result.getPaymentDate());
    }
}
