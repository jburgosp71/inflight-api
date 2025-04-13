package com.airshop.inflightapi.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter @Setter
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private double price;
    private String imageUrl;
    private int stock;

    @ManyToOne
    private CategoryEntity category;
}
