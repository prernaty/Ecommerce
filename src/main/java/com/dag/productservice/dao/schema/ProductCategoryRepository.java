package com.dag.productservice.dao.schema;

import com.dag.productservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductCategoryRepository extends JpaRepository<Category, String> {
}
