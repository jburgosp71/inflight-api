package com.airshop.inflightapi.application.port.in;

import java.util.List;
import com.airshop.inflightapi.domain.model.Product;

public interface GetProductsUseCase {
    List<Product> getAll(Long categoryId);
}