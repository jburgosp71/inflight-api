package com.airshop.inflightapi.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.airshop.inflightapi.adapter.persistence.entity.ProductEntity;

import java.util.List;

public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCategoryId(Long categoryId);
}
