package com.airshop.inflightapi.adapter.persistence;

import com.airshop.inflightapi.adapter.persistence.mapper.ProductMapper;
import com.airshop.inflightapi.adapter.persistence.repository.SpringDataProductRepository;
import com.airshop.inflightapi.application.port.out.ProductRepository;
import com.airshop.inflightapi.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaProductRepositoryAdapter implements ProductRepository {

    private final SpringDataProductRepository springRepo;

    @Override
    public List<Product> findAll(Long categoryId) {
        if (categoryId != null) {
            return springRepo.findByCategoryId(categoryId)
                    .stream().map(ProductMapper::toDomain)
                    .collect(Collectors.toList());
        } else {
            return springRepo.findAll()
                .stream().map(ProductMapper::toDomain)
                .collect(Collectors.toList());
        }
    }

    @Override
    public Product save(Product product) {
        return ProductMapper.toDomain(
                springRepo.save(ProductMapper.toEntity(product))
        );
    }
}
