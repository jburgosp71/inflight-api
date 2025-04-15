package com.airshop.inflightapi.controller;

import com.airshop.inflightapi.adapter.web.dto.CategoryRequest;
import com.airshop.inflightapi.adapter.web.dto.ProductRequest;
import com.airshop.inflightapi.adapter.web.dto.ProductResponse;
import com.airshop.inflightapi.domain.model.Category;
import com.airshop.inflightapi.domain.model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private Long snacksCategoryId;

    @BeforeAll
    void setupCategory() {
        String baseUrl = "http://localhost:" + port + "/api/categories";

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Snacks");

        ResponseEntity<Category> categoryResponse = restTemplate.postForEntity(baseUrl, categoryRequest, Category.class);
        snacksCategoryId = Objects.requireNonNull(categoryResponse.getBody()).getId();

        assertThat(snacksCategoryId).isNotNull();
    }

    @Test
    void testCreateAndFetchProduct() {
        String baseUrl = "http://localhost:" + port + "/api/products";

        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Potato Chips");
        productRequest.setPrice(2.5);
        productRequest.setStock(100);
        productRequest.setCategoryId(snacksCategoryId);

        ResponseEntity<Product> createResponse = restTemplate.postForEntity(baseUrl, productRequest, Product.class);
        assertThat(createResponse.getStatusCode().is2xxSuccessful()).isTrue();

        Product createdProduct = createResponse.getBody();
        assertThat(createdProduct).isNotNull();
        assertThat(createdProduct.getName()).isEqualTo("Potato Chips");
        assertThat(createdProduct.getCategory().getId()).isEqualTo(snacksCategoryId);

        ResponseEntity<ProductResponse[]> getResponse =
                restTemplate.getForEntity(baseUrl + "?categoryId=" + snacksCategoryId, ProductResponse[].class);

        assertThat(getResponse.getStatusCode().is2xxSuccessful()).isTrue();
        ProductResponse[] products = getResponse.getBody();
        assertThat(products).isNotEmpty();

        boolean productFound = Arrays.stream(products)
                .anyMatch(p -> p.getName().equals("Potato Chips") &&
                        p.getCategoryId().equals(snacksCategoryId));
        assertThat(productFound).isTrue();
    }
}
