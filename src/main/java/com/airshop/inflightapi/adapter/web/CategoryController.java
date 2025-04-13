package com.airshop.inflightapi.adapter.web;

import com.airshop.inflightapi.application.port.in.CreateCategoryUseCase;
import com.airshop.inflightapi.application.port.in.GetCategoriesUseCase;
import com.airshop.inflightapi.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final GetCategoriesUseCase getCategoriesUseCase;
    private final CreateCategoryUseCase createCategoryUseCase;

    @GetMapping
    public List<Category> getAll() {
        return getCategoriesUseCase.getAll();
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return createCategoryUseCase.create(category);
    }
}
