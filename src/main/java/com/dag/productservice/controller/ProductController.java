package com.dag.productservice.controller;

import com.dag.productservice.dto.ProductRequestDto;
import com.dag.productservice.dto.ProductResponseDto;
import com.dag.productservice.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ProductResponseDto[]> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") String id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseDto> deleteproductById(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                productService.deleteproductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto requestDto) {
        return ResponseEntity.ok(productService.createProduct(requestDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductResponseDto> updateProductById(@PathVariable("id") String id,
                                                                @RequestBody ProductRequestDto requestDto) {
        return new ResponseEntity<>(productService.updateProductById(id, requestDto), HttpStatus.OK);
    }

}