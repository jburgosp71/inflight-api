package com.airshop.inflightapi.application.port.out;

import com.airshop.inflightapi.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();
    Product save(Product product);
    Optional<Product> findById(Long id);
}
