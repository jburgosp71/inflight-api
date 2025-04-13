package com.airshop.inflightapi.application.port.in;

import com.airshop.inflightapi.domain.model.Product;

public interface CreateProductUseCase {
    Product create(Product product);
}
