package com.andretask.salesmanagement.controller;

import com.andretask.salesmanagement.controllers.ProductController;
import com.andretask.salesmanagement.controllers.dto.ProductCreateDto;
import com.andretask.salesmanagement.controllers.dto.ProductResponseDto;
import com.andretask.salesmanagement.controllers.dto.ProductUpdateDto;
import com.andretask.salesmanagement.models.Product;
import com.andretask.salesmanagement.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testCreateProduct() throws Exception {
        ProductCreateDto createDto = new ProductCreateDto("Laptop", "High-end gaming laptop", "Electronics");
        Product createdProduct = new Product(1L, "Laptop", "High-end gaming laptop", "Electronics", null);
        ProductResponseDto responseDto = new ProductResponseDto(createdProduct.getId(), "Product created successfully");

        Mockito.when(productService.createProduct(Mockito.any(ProductCreateDto.class))).thenReturn(createdProduct);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Product created successfully")));
    }

    @Test
    public void testFetchProducts() throws Exception {
        List<Product> products = Arrays.asList(new Product(1L, "Laptop", "High-end gaming laptop", "Electronics", null));
        Mockito.when(productService.fetchProducts()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Laptop")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description", Matchers.is("High-end gaming laptop")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category", Matchers.is("Electronics")));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        ProductUpdateDto updateDto = new ProductUpdateDto("Updated Laptop", "Updated description", "Updated category");
        Product updatedProduct = new Product(1L, "Updated Laptop", "Updated description", "Updated category", null);
        ProductResponseDto responseDto = new ProductResponseDto(updatedProduct.getId(), "Product updated successfully");

        Mockito.when(productService.updateProduct(Mockito.eq(1L), Mockito.any(ProductUpdateDto.class))).thenReturn(updatedProduct);

        mockMvc.perform(MockMvcRequestBuilders.put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Product updated successfully")));
    }



}
