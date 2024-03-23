package com.andretask.salesmanagement.controllers;

import com.andretask.salesmanagement.models.Sale;
import com.andretask.salesmanagement.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {
    @Autowired
    private SaleService saleService;

    @GetMapping
    public List<Sale> fetchSales() {
        return saleService.fetchSales();
    }

    @PostMapping
    public Sale createSale(@RequestBody Sale sale) {
        return saleService.createSale(sale);
    }

    @PutMapping("/{saleId}/transactions/{transactionId}")
    public ResponseEntity<Sale> updateTransaction(@PathVariable Long saleId, @PathVariable Long transactionId,
                                                  @RequestParam int quantity, @RequestParam double price) {
        Sale sale = saleService.updateTransaction(saleId, transactionId, quantity, price);
        if (sale == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sale);
    }
}

