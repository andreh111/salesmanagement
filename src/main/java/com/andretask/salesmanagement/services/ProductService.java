package com.andretask.salesmanagement.services;

import com.andretask.salesmanagement.exceptions.DuplicateProductException;
import com.andretask.salesmanagement.exceptions.ProductNotFoundException;
import com.andretask.salesmanagement.models.Product;
import com.andretask.salesmanagement.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> fetchProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        Optional<Product> existingProduct = productRepository.findById(product.getId());
        if (existingProduct.isPresent()) {
            throw new DuplicateProductException("Product with name " + product.getName() + " already exists");
        }
        product.setCreationDate(new Date());
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setCategory(updatedProduct.getCategory());
            return productRepository.save(product);
        }
        throw new ProductNotFoundException("Product with id " + id + " not found");
    }
}
