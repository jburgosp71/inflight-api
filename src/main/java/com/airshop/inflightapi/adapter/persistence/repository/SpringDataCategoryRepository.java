package com.airshop.inflightapi.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.airshop.inflightapi.adapter.persistence.entity.CategoryEntity;

public interface SpringDataCategoryRepository extends JpaRepository<CategoryEntity, Long> { }
