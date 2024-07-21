package com.dag.productservice.services.category;

import com.dag.productservice.dao.schema.ProductCategoryRepository;
import com.dag.productservice.dto.ProductCategoryRequestDto;
import com.dag.productservice.dto.CategoryResponseDto;
import com.dag.productservice.dto.ProductResponseDto;
import com.dag.productservice.exceptionhandlers.exceptions.NotFoundException;
import com.dag.productservice.models.Category;
import com.dag.productservice.models.Product;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public CategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public CategoryResponseDto findCategoryById(String id) {

        Category category = productCategoryRepository.findById(id).orElse(null);

        assert category != null;

        return convertToResponseDto(category);
    }

    /**
     * @return
     */
    @Override
    public CategoryResponseDto[] findCategories() {
        List<Category> categories = productCategoryRepository.findAll();
        return categories.stream().
                map(this::convertToResponseDto).toArray(CategoryResponseDto[]::new);
    }


    @Override
    public CategoryResponseDto[] findCategoriesIn(List<String> ids) {
        List<UUID> uuids = new ArrayList<>();
        ids.forEach(id -> uuids.add(getUuidFromString(id)));
        List<Category> categories = productCategoryRepository.findAllById(ids);
        return categories.stream().
                map(this::convertToResponseDto).toArray(CategoryResponseDto[]::new);
    }


    @Override
    public CategoryResponseDto createCategory(ProductCategoryRequestDto productCategoryRequestDto) {
        if (Objects.isNull(productCategoryRequestDto))
            return null;
        Category category = new Category();
        category.setName(productCategoryRequestDto.getName());
        category.setDescription(productCategoryRequestDto.getDescription());
        category.setProducts(new ArrayList<>());
        productCategoryRepository.save(category);
        return convertToResponseDto(category);
    }


    @Override
    public CategoryResponseDto updateCategoryById(String id, ProductCategoryRequestDto productCategoryRequestDto) {
        if (id == null || productCategoryRequestDto == null) {
            log.error("No category found for id {}",id);
            throw new NotFoundException("Category for requested Id not found");
        }
        UUID uuid = getUuidFromString(id);
        Category category = productCategoryRepository.findById(id).orElse(null);
        if (Objects.isNull(category)) {
            log.error("Category not found for the request product");
            throw new NotFoundException("Category for requested Id not found");
        }
        category.setName(productCategoryRequestDto.getName() != null ?
                productCategoryRequestDto.getName() : category.getName());
        category.setDescription(productCategoryRequestDto.getDescription() != null ?
                productCategoryRequestDto.getDescription() : category.getDescription());
        productCategoryRepository.save(category);
        log.info("product category successfully saved");
        return convertToResponseDto(category);
    }


    @Override
    @Transactional
    public CategoryResponseDto deleteCategoryById(String id) {
        if (id == null)
            throw new NotFoundException("No Category found for");
        UUID uuid = getUuidFromString(id);
        Category category = productCategoryRepository.findById(id).orElse(null);
        if (category == null)
            throw new NotFoundException("Category for requested Id not found");
        productCategoryRepository.delete(category);
        return convertToResponseDto(category);
    }

    private ProductResponseDto[] convertToProductsDto(List<Product> products) {
        if (products == null)
            return null;
        return products.stream().map(ProductResponseDto::new).toArray(ProductResponseDto[]::new);
    }

    private CategoryResponseDto convertToResponseDto(Category category) {

        return CategoryResponseDto.builder().id(category.getId().toString()).
                                            name(category.getName()).
                                            description(category.getDescription()).
                                            products(convertToProductsDto(category.getProducts())).build();
    }

    private UUID getUuidFromString(String id) {
        return UUID.fromString(id);
    }
}
