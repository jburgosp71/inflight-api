package com.airshop.inflightapi.adapter.web;

import com.airshop.inflightapi.adapter.persistence.mapper.DtoMapper;
import com.airshop.inflightapi.adapter.web.dto.ProductRequest;
import com.airshop.inflightapi.adapter.web.dto.ProductResponse;
import com.airshop.inflightapi.application.port.in.CreateProductUseCase;
import com.airshop.inflightapi.application.port.in.GetProductsUseCase;
import com.airshop.inflightapi.application.port.in.GetCategoriesUseCase;
import com.airshop.inflightapi.domain.model.Category;
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
    private final GetCategoriesUseCase getCategoryUseCase;

    @GetMapping
    public List<ProductResponse> getAll(@RequestParam(value = "categoryId", required = false) Long categoryId) {
        return getProductsUseCase.getAll(categoryId)
                .stream()
                .map(DtoMapper::toResponse)
                .toList();
    }

    @PostMapping
    public Product create(@RequestBody ProductRequest request) {
        Category category = getCategoryUseCase.getById(request.getCategoryId());
        Product product = DtoMapper.toDomain(request, category);

        return createProductUseCase.create(product);
    }
}
