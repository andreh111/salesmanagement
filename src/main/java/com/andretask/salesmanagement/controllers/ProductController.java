package com.andretask.salesmanagement.controllers;
import com.andretask.salesmanagement.controllers.dto.ProductCreateDto;
import com.andretask.salesmanagement.controllers.dto.ProductUpdateDto;
import com.andretask.salesmanagement.models.Product;
import com.andretask.salesmanagement.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public Product createProduct(@RequestBody ProductCreateDto productDto) {
        return productService.createProduct(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateDto productDto) {
        Product product = productService.updateProduct(id, productDto);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }
}
