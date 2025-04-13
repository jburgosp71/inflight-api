package com.airshop.inflightapi.controller;

import com.airshop.inflightapi.adapter.web.dto.CategoryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate("admin", "admin");

    @Test
    void shouldReturnAllCategories() {
        String baseUrl = "http://localhost:" + port + "/api/categories";

        ResponseEntity<CategoryResponse[]> response =
                restTemplate.getForEntity(baseUrl, CategoryResponse[].class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotEmpty();
    }
}