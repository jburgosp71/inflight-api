package com.airshop.inflightapi.application.port.out;

import com.airshop.inflightapi.domain.model.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll(Long categoryId);
    Product save(Product product);
    boolean checkStock(Long productId, int quantity);
    Product findById(Long productId);
}
