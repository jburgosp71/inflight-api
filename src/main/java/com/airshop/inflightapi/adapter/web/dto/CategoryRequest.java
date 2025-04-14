package com.airshop.inflightapi.adapter.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRequest {
    private Long id;
    private String name;
    private Long parentCategoryId;
}
