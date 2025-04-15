package com.airshop.inflightapi.controller;

import com.airshop.inflightapi.adapter.web.dto.OrderResponse;
import com.airshop.inflightapi.domain.model.Order;
import com.airshop.inflightapi.domain.model.PaymentDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate, authRestTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/orders";
        authRestTemplate = restTemplate.withBasicAuth("admin", "admin");
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void testCreateOrder() {


        Order request = new Order();
        request.setSeatLetter("C");
        request.setSeatNumber(7);

        ResponseEntity<OrderResponse> response = authRestTemplate.postForEntity(baseUrl, request, OrderResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("C", response.getBody().getSeatLetter());
        assertEquals(7, response.getBody().getSeatNumber());
        assertNotNull(response.getBody().getId());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void testGetOrder() {
        Order request = new Order();
        request.setSeatLetter("A");
        request.setSeatNumber(1);
        OrderResponse created = authRestTemplate.postForEntity(baseUrl, request, OrderResponse.class).getBody();

        assert created != null;
        ResponseEntity<OrderResponse> response = authRestTemplate.getForEntity(baseUrl + "/" + created.getId(), OrderResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(created.getId(), response.getBody().getId());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void testFinalizeOrder() {
        Order request = new Order();
        request.setSeatLetter("B");
        request.setSeatNumber(3);
        OrderResponse created = authRestTemplate.postForEntity(baseUrl, request, OrderResponse.class).getBody();

        Order finalizeRequest = new Order();
        PaymentDetails payment = new PaymentDetails();
        payment.setCardToken("123456789");
        payment.setGateway("mock");
        finalizeRequest.setPaymentDetails(payment);

        assert created != null;
        ResponseEntity<OrderResponse> response = authRestTemplate.postForEntity(
                baseUrl + "/" + created.getId() + "/finalize",
                finalizeRequest,
                OrderResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getStatus());
        System.out.println("Estado de la orden finalizada: " + response.getBody().getStatus());
    }
}
