package com.dag.productservice.dto;

import lombok.Getter;
import lombok.Setter;
/* To be used to store the response from downstream service APIs */

@Getter
@Setter
public class ProductApiResponseDto {
    Integer id;
    String title;
    Double price;
    String category;
    String description;
}