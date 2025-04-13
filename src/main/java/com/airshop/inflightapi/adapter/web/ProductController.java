package com.airshop.inflightapi.adapter.web;

import com.airshop.inflightapi.application.port.in.CreateProductUseCase;
import com.airshop.inflightapi.application.port.in.GetProductsUseCase;
import com.airshop.inflightapi.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final GetProductsUseCase getProductsUseCase;
    private final CreateProductUseCase createProductUseCase;

    @GetMapping
    public List<Product> getAll(@RequestParam(value = "categoryId", required = false) Long categoryId) {
        return getProductsUseCase.getAll(categoryId);
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return createProductUseCase.create(product);
    }
}
