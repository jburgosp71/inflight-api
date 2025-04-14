package com.airshop.inflightapi.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter @Setter
@NoArgsConstructor
public class OrderItemEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private ProductEntity product;


    private int quantity;
}
