package com.andretask.salesmanagement.repositories;

import com.andretask.salesmanagement.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
