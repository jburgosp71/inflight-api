package com.airshop.inflightapi.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class OrderItem {
    private Long id;
    //private Product product;
    private Long productId;
    private int quantity;

    //public OrderItem(Long id, Product product, int quantity) {
    public OrderItem(Long id, Long productId, int quantity) {
        this.id = id;
        //this.product = product;
        this.productId = productId;
        this.quantity = quantity;
    }
}

