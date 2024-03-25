package com.andretask.salesmanagement.controllers;
import com.andretask.salesmanagement.controllers.dto.ClientResponseDto;
import com.andretask.salesmanagement.controllers.dto.ProductCreateDto;
import com.andretask.salesmanagement.controllers.dto.ProductResponseDto;
import com.andretask.salesmanagement.controllers.dto.ProductUpdateDto;
import com.andretask.salesmanagement.exceptions.ClientNotFoundException;
import com.andretask.salesmanagement.models.Client;
import com.andretask.salesmanagement.models.Product;
import com.andretask.salesmanagement.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> fetchProducts() {
        return productService.fetchProducts();
    }

    @PostMapping
    public ProductResponseDto createProduct(@Validated @RequestBody ProductCreateDto productDto) {
        Product createdProduct = productService.createProduct(productDto);
        return new ProductResponseDto(createdProduct.getId(), "Product created successfully");
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @Validated @RequestBody ProductUpdateDto productDto) {
        try {
            Product updatedProduct = productService.updateProduct(id, productDto);
            return ResponseEntity.ok(new ProductResponseDto(updatedProduct.getId(), "Product updated successfully"));
        } catch (ClientNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
