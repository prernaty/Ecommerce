package com.dag.productservice.services.category;

import com.dag.productservice.dao.schema.ProductCategoryRepository;
import com.dag.productservice.dto.ProductCategoryRequestDto;
import com.dag.productservice.dto.CategoryResponseDto;
import com.dag.productservice.dto.ProductResponseDto;
import com.dag.productservice.exceptionhandlers.exceptions.NotFoundException;
import com.dag.productservice.models.Category;
import com.dag.productservice.models.Product;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LocalCategoriesService implements CategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public LocalCategoriesService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public CategoryResponseDto findCategoryById(String id) {
        UUID uuid = getUuidFromString(id);

        Category category = productCategoryRepository.findById(uuid).orElse(null);

        assert category != null;

        return serialistToCategoryDto(category);
    }

    /**
     * @return
     */
    @Override
    public CategoryResponseDto[] findCategories() {
        List<Category> categories = productCategoryRepository.findAll();
        return categories.stream().
                map(this::serialistToCategoryDto).toArray(CategoryResponseDto[]::new);
    }


    /**
     * @param ids
     * @return
     */
    @Override
    public CategoryResponseDto[] findCategoriesIn(List<String> ids) {
        List<UUID> uuids = new ArrayList<>();
        ids.forEach(id -> uuids.add(getUuidFromString(id)));
        List<Category> categories = productCategoryRepository.findAllById(uuids);
        return categories.stream().
                map(this::serialistToCategoryDto).toArray(CategoryResponseDto[]::new);
    }

    /**
     * @param productCategoryRequestDto
     * @return
     */
    @Override
    public CategoryResponseDto createCategory(ProductCategoryRequestDto productCategoryRequestDto) {
        if (productCategoryRequestDto == null)
            return null;
        Category category = new Category();
        category.setName(productCategoryRequestDto.getName());
        category.setDescription(productCategoryRequestDto.getDescription());
        category.setProducts(new ArrayList<>());
        productCategoryRepository.save(category);
        return serialistToCategoryDto(category);
    }

    /**
     * @param id
     * @param productCategoryRequestDto
     * @return
     */
    @Override
    public CategoryResponseDto updateCategoryById(String id, ProductCategoryRequestDto productCategoryRequestDto) {
        if (id == null || productCategoryRequestDto == null)
            throw new NotFoundException("Category for requested Id not found");
        UUID uuid = getUuidFromString(id);
        Category category = productCategoryRepository.findById(uuid).orElse(null);
        if (category == null)
            throw new NotFoundException("Category for requested Id not found");
        category.setName(productCategoryRequestDto.getName() != null ?
                productCategoryRequestDto.getName() : category.getName());
        category.setDescription(productCategoryRequestDto.getDescription() != null ?
                productCategoryRequestDto.getDescription() : category.getDescription());
        productCategoryRepository.save(category);
        return serialistToCategoryDto(category);
    }


    /**
     * @param id
     * @return
     */
    @Override
    @Transactional
    public CategoryResponseDto deleteCategoryById(String id) {
        if (id == null)
            throw new NotFoundException("Category for requested Id not found");
        UUID uuid = getUuidFromString(id);
        Category category = productCategoryRepository.findById(uuid).orElse(null);
        if (category == null)
            throw new NotFoundException("Category for requested Id not found");
        productCategoryRepository.delete(category);
        return serialistToCategoryDto(category);
    }

    private ProductResponseDto[] serialiseToProductsDto(List<Product> products) {
        if (products == null)
            return null;
        return products.stream().map(ProductResponseDto::new).toArray(ProductResponseDto[]::new);
    }

    private CategoryResponseDto serialistToCategoryDto(Category category) {
        return new CategoryResponseDto(category.getId().toString(), category.getName(), category.getDescription(),
                serialiseToProductsDto(category.getProducts()));
    }

    private UUID getUuidFromString(String id) {
        return UUID.fromString(id);
    }
}
