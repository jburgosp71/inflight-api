package com.airshop.inflightapi.controller;

import com.airshop.inflightapi.adapter.web.dto.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate("admin", "admin");

    @Test
    void shouldReturnAllProducts() {
        String baseUrl = "http://localhost:" + port + "/api/products";

        ResponseEntity<ProductResponse[]> response =
                restTemplate.getForEntity(baseUrl, ProductResponse[].class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0); // Asume que hay productos cargados
    }
}
