package com.airshop.inflightapi.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Product {

    private Long id;
    private String name;
    private double price;
    private String imageUrl;
    private int stock;
    private Category category;

    public Product(Long id, String name, double price, String imageUrl, int stock, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.category = category;
    }
}

