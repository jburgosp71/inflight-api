package com.airshop.inflightapi.application.port.in;

import com.airshop.inflightapi.domain.model.Category;

public interface CreateCategoryUseCase {
    Category create(Category category);
}