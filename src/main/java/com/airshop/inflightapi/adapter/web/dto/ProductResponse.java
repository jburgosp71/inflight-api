package com.airshop.inflightapi.adapter.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private double price;
    private String imageUrl;
    private int stock;
    private Long categoryId;
    private String categoryName;
}
