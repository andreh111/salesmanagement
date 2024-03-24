package com.andretask.salesmanagement.services;

import com.andretask.salesmanagement.exceptions.SaleNotFoundException;
import com.andretask.salesmanagement.models.Sale;
import com.andretask.salesmanagement.models.Transaction;
import com.andretask.salesmanagement.repositories.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {
    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository){
        this.saleRepository = saleRepository;
    }

    public List<Sale> fetchSales() {
        return saleRepository.findAll();
    }

    public Sale createSale(Sale sale) {
        sale.setCreationDate(new Date());
        for (Transaction transaction : sale.getTransactions()) {
            transaction.setSale(sale);
        }
        return saleRepository.save(sale);
    }

    public Sale updateTransaction(Long saleId, Long transactionId, int quantity, double price) {
        Optional<Sale> optionalSale = saleRepository.findById(saleId);
        if (optionalSale.isPresent()) {
            Sale sale = optionalSale.get();
            for (Transaction transaction : sale.getTransactions()) {
                if (transaction.getId().equals(transactionId)) {
                    transaction.setQuantity(quantity);
                    transaction.setPrice(price);
                }
            }
            return saleRepository.save(sale);
        }
        throw new SaleNotFoundException("Sale with id " + saleId + " not found");
    }
}
