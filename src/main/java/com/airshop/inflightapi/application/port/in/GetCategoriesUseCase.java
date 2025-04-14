package com.airshop.inflightapi.application.port.in;

import java.util.List;
import com.airshop.inflightapi.domain.model.Category;

public interface GetCategoriesUseCase {
    List<Category> getAll();
    Category getById(Long id);
}
