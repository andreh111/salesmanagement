package com.andretask.salesmanagement.services;

import com.andretask.salesmanagement.controllers.dto.ProductCreateDto;
import com.andretask.salesmanagement.controllers.dto.ProductUpdateDto;
import com.andretask.salesmanagement.exceptions.DuplicateProductException;
import com.andretask.salesmanagement.exceptions.ProductNotFoundException;
import com.andretask.salesmanagement.models.Product;
import com.andretask.salesmanagement.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing products.
 */
@Service
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * Constructs a new ProductService with the given ProductRepository.
     *
     * @param productRepository the repository used for product operations
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Fetches all products from the repository.
     *
     * @return a list of all products
     */
    public List<Product> fetchProducts() {
        return productRepository.findAll();
    }

    /**
     * Creates a new product and saves it to the repository.
     *
     * @param productDto the product data transfer object containing the product information
     * @return the created product
     * @throws DuplicateProductException if a product with the same name already exists
     */
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

    /**
     * Updates a product with the given ID using the provided updated product information.
     *
     * @param id the ID of the product to be updated
     * @param productDto the updated product data transfer object
     * @return the updated product
     * @throws ProductNotFoundException if a product with the specified ID is not found
     */
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