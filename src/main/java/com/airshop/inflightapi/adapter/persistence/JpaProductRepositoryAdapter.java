package com.airshop.inflightapi.adapter.persistence;

import com.airshop.inflightapi.adapter.persistence.entity.ProductEntity;
import com.airshop.inflightapi.adapter.persistence.mapper.ProductMapper;
import com.airshop.inflightapi.adapter.persistence.repository.SpringDataProductRepository;
import com.airshop.inflightapi.application.port.out.ProductRepository;
import com.airshop.inflightapi.domain.model.Product;
import jakarta.persistence.EntityNotFoundException;
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

    @Override
    public boolean checkStock(Long productId, int quantity) {
        Optional<ProductEntity> productEntityOptional =
                springRepo.findById(productId);

        return productEntityOptional
                .map(entity -> entity.getStock() >= quantity)
                .orElse(false);
    }

    @Override
    public Product findById(Long productId) {
        return springRepo.findById(productId)
                .map(ProductMapper::toDomain)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + productId));
    }
}
