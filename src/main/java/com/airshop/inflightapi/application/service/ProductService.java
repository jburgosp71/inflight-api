package com.airshop.inflightapi.application.service;

import com.airshop.inflightapi.application.port.in.CreateProductUseCase;
import com.airshop.inflightapi.application.port.in.GetProductsUseCase;
import com.airshop.inflightapi.application.port.out.ProductRepository;
import com.airshop.inflightapi.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements GetProductsUseCase, CreateProductUseCase {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAll(Long categoryId) {
        return productRepository.findAll();
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }
}
