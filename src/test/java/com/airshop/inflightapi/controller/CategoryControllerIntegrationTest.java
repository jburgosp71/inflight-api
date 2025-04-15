package com.airshop.inflightapi.controller;

import com.airshop.inflightapi.adapter.web.dto.CategoryRequest;
import com.airshop.inflightapi.adapter.web.dto.CategoryResponse;
import com.airshop.inflightapi.domain.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class CategoryControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/categories";
    }

    @Test
    void testShouldCreateAndRetrieveCategoriesWithParent() {
        var authRestTemplate = restTemplate.withBasicAuth("admin", "admin");

        CategoryRequest parentRequest = new CategoryRequest();
        parentRequest.setName("Snacks");

        ResponseEntity<Category> parentResponse = authRestTemplate.postForEntity(
                baseUrl(), parentRequest, Category.class);

        assertThat(parentResponse.getStatusCode().is2xxSuccessful()).isTrue();
        Category parentCategory = parentResponse.getBody();
        assertThat(parentCategory).isNotNull();
        assertThat(parentCategory.getId()).isNotNull();

        CategoryRequest childRequest = new CategoryRequest();
        childRequest.setName("Chips");
        childRequest.setParentCategoryId(parentCategory.getId());

        ResponseEntity<Category> childResponse = authRestTemplate.postForEntity(
                baseUrl(), childRequest, Category.class);

        assertThat(childResponse.getStatusCode().is2xxSuccessful()).isTrue();
        Category childCategory = childResponse.getBody();
        assertThat(childCategory).isNotNull();
        assertThat(childCategory.getParentCategory()).isNotNull();
        assertThat(childCategory.getParentCategory().getId()).isEqualTo(parentCategory.getId());

        ResponseEntity<CategoryResponse[]> getAllResponse = authRestTemplate.getForEntity(
                baseUrl(), CategoryResponse[].class);

        assertThat(getAllResponse.getStatusCode().is2xxSuccessful()).isTrue();
        CategoryResponse[] allCategories = getAllResponse.getBody();
        assertThat(allCategories).isNotEmpty();

        boolean childFound = Arrays.stream(allCategories)
                .anyMatch(cat -> "Chips".equals(cat.getName()) &&
                        cat.getParentCategoryId() != null &&
                        cat.getParentCategoryId().equals(parentCategory.getId()));

        assertThat(childFound).isTrue();
    }
}