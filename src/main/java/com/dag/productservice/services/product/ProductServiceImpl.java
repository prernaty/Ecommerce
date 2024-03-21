package com.dag.productservice.services.product;

import com.dag.productservice.dao.ProductsRepository;
import com.dag.productservice.dao.schema.ProductCategoryRepository;
import com.dag.productservice.dto.ProductRequestDto;
import com.dag.productservice.dto.ProductResponseDto;
import com.dag.productservice.exceptionhandlers.exceptions.NotFoundException;
import com.dag.productservice.models.Category;
import com.dag.productservice.models.Product;
import com.dag.productservice.validators.Validators;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Primary
public class ProductServiceImpl implements ProductService {

    private final ProductsRepository productsRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductServiceImpl(ProductsRepository productsRepository,
                              ProductCategoryRepository categoryRepository) {
        this.productsRepository = productsRepository;
        this.productCategoryRepository = categoryRepository;
    }

    @Override
    public ProductResponseDto getProductById(String id) {
        Optional<Product> product = this.productsRepository.findById(UUID.fromString(id));
        if (product.isEmpty()) {
            throw new NotFoundException("Product not found");
        } else
            return new ProductResponseDto(product.get());
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        if (Validators.NON_NULL_CHECK.get().isValid(requestDto.getName()) &&
                Validators.NON_NULL_CHECK.get().isValid(requestDto.getDescription()) &&
                Validators.NON_NULL_CHECK.get().isValid(requestDto.getTitle())) {
            Product product = new Product();
            product.setName(requestDto.getName());
            product.setPrice(requestDto.getPrice());
            product.setDescription(requestDto.getDescription());
            product.setPrice(requestDto.getPrice());
            product.setTitle(requestDto.getTitle());
            if (requestDto.getCategory() != null && !requestDto.getCategory().isEmpty()) {
                if (!Validators.UUID_VALIDATOR.get().isValid(requestDto.getCategory()))
                    throw new IllegalArgumentException("Invalid input");
                Category category = this.productCategoryRepository.findById(
                        UUID.fromString(requestDto.getCategory())).orElse(null);
                product.setCategory(category);
            }
            Product createdProduct = this.productsRepository.save(product);
            return new ProductResponseDto(createdProduct);
        }
        throw new IllegalArgumentException("Invalid input");
    }

    @Override
    public ProductResponseDto[] getAllProducts() {
        return this.productsRepository.findAll().stream()
                .map(ProductResponseDto::new).toArray(ProductResponseDto[]::new);
    }

    @Override
    public ProductResponseDto deleteproductById(String id) {
        if (!Validators.UUID_VALIDATOR.get().isValid(id))
            throw new IllegalArgumentException("Invalid input");
        UUID uuid = UUID.fromString(id);
        if (this.productsRepository.existsById(uuid)) {
            Product product = this.productsRepository.findById(uuid).get();
            this.productsRepository.deleteById(uuid);
            return new ProductResponseDto(product);
        }
        throw new NotFoundException("Product not found");
    }

    @Override
    public ProductResponseDto updateProductById(String id, ProductRequestDto requestDto) {
        if (Validators.NON_NULL_CHECK.get().isValid(requestDto.getName()) &&
                Validators.NON_NULL_CHECK.get().isValid(requestDto.getDescription()) &&
                Validators.NON_NULL_CHECK.get().isValid(requestDto.getTitle()) &&
                Validators.UUID_VALIDATOR.get().isValid(id)) {
            UUID uuid = UUID.fromString(id);
            if (this.productsRepository.existsById(uuid)) {
                Product product = this.productsRepository.findById(uuid).orElse(null);
                product.setTitle(requestDto.getTitle());
                product.setName(requestDto.getName());
                product.setDescription(requestDto.getDescription());
                product.setPrice(requestDto.getPrice());
                Product updatedProduct = this.productsRepository.save(product);
                return new ProductResponseDto(updatedProduct);
            } else
                throw new NotFoundException("Product not found");
        }
        throw new IllegalArgumentException("Invalid input");
    }
}
