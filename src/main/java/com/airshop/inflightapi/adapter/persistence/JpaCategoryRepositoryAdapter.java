package com.airshop.inflightapi.adapter.persistence;

import com.airshop.inflightapi.adapter.persistence.entity.CategoryEntity;
import com.airshop.inflightapi.adapter.persistence.mapper.CategoryMapper;
import com.airshop.inflightapi.adapter.persistence.repository.SpringDataCategoryRepository;
import com.airshop.inflightapi.application.port.out.CategoryRepository;
import com.airshop.inflightapi.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaCategoryRepositoryAdapter implements CategoryRepository {

    private final SpringDataCategoryRepository springRepo;

    @Override
    public List<Category> findAll() {
        return springRepo.findAll()
                .stream().map(CategoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Category save(Category category) {
        CategoryEntity saved = springRepo.save(CategoryMapper.toEntity(category));
        return CategoryMapper.toDomain(saved);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return springRepo.findById(id).map(CategoryMapper::toDomain);
    }
}
