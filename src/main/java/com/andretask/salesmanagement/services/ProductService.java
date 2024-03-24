package com.andretask.salesmanagement.services;

import com.andretask.salesmanagement.dto.ProductCreateDto;
import com.andretask.salesmanagement.dto.ProductUpdateDto;
import com.andretask.salesmanagement.exceptions.DuplicateProductException;
import com.andretask.salesmanagement.exceptions.ProductNotFoundException;
import com.andretask.salesmanagement.models.Product;
import com.andretask.salesmanagement.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> fetchProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(ProductCreateDto productDto) {
        Optional<Product> existingProduct = productRepository.findByName(productDto.getName());
        if (existingProduct.isPresent()) {
            throw new DuplicateProductException("Product with name " + productDto.getName() + " already exists");
        }
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
        product.setCreationDate(new Date());
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductUpdateDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (productDto.getName() != null) {
                product.setName(productDto.getName());
            }
            if (productDto.getDescription() != null) {
                product.setDescription(productDto.getDescription());
            }
            if (productDto.getCategory() != null) {
                product.setCategory(productDto.getCategory());
            }
            return productRepository.save(product);
        }
        throw new ProductNotFoundException("Product with id " + id + " not found");
    }
}
