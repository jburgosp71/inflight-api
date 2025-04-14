package com.airshop.inflightapi.adapter.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ProductRequest {
    private String name;
    private double price;
    private int stock;
    private String imageUrl;
    private Long categoryId;
}