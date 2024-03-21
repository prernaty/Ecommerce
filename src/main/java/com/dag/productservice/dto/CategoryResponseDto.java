package com.dag.productservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {
    String id;
    String name;
    String description;
    ProductResponseDto[] products;
}
