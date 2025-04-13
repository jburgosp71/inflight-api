package com.airshop.inflightapi.application.port.out;

import java.util.List;
import java.util.Optional;

import com.airshop.inflightapi.domain.model.Category;

public interface CategoryRepository {
    List<Category> findAll();
    Category save(Category category);
    Optional<Category> findById(Long id);
}
