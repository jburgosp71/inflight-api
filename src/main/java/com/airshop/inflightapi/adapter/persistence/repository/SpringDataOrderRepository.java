package com.airshop.inflightapi.adapter.persistence.repository;

import com.airshop.inflightapi.adapter.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataOrderRepository extends JpaRepository<OrderEntity, Long> {
}
