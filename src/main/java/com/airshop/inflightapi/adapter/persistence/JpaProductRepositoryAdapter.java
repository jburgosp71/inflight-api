package com.airshop.inflightapi.adapter.persistence;

import com.airshop.inflightapi.adapter.persistence.mapper.ProductMapper;
import com.airshop.inflightapi.adapter.persistence.repository.SpringDataProductRepository;
import com.airshop.inflightapi.application.port.out.ProductRepository;
import com.airshop.inflightapi.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaProductRepositoryAdapter implements ProductRepository {

    private final SpringDataProductRepository springRepo;

    @Override
    public List<Product> findAll() {
        return springRepo.findAll()
                .stream().map(ProductMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> findById(Long id) {
        return springRepo.findById(id).map(ProductMapper::toDomain);
    }

    @Override
    public Product save(Product product) {
        return ProductMapper.toDomain(
                springRepo.save(ProductMapper.toEntity(product))
        );
    }
}
