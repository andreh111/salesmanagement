package com.andretask.salesmanagement.controllers;

import com.andretask.salesmanagement.controllers.dto.SaleCreateDto;
import com.andretask.salesmanagement.controllers.dto.SaleResponseDto;
import com.andretask.salesmanagement.controllers.dto.SaleUpdateDto;
import com.andretask.salesmanagement.services.SaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {
    @Autowired
    private SaleService saleService;

    @GetMapping
    public List<SaleResponseDto> fetchSales() {
        return saleService.fetchSales();
    }

    @PostMapping
    public SaleResponseDto createSale(@Validated @RequestBody SaleCreateDto saleCreateDto) {
        return saleService.createSale(saleCreateDto);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<SaleResponseDto> updateSale(@PathVariable Long id,@Validated @RequestBody SaleUpdateDto updateSaleDto) {
        SaleResponseDto updatedSale = saleService.updateSale(id, updateSaleDto);
        if (updatedSale == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedSale);
    }


}
