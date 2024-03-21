package com.dag.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductCategoryRequestDto {
    String name;
    String description;
    ProductRequestDto[] products;
}
