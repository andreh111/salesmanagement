package com.andretask.salesmanagement.services;

import com.andretask.salesmanagement.dto.*;
import com.andretask.salesmanagement.exceptions.SaleNotFoundException;
import com.andretask.salesmanagement.exceptions.TransactionNotFoundException;
import com.andretask.salesmanagement.models.Client;
import com.andretask.salesmanagement.models.Product;
import com.andretask.salesmanagement.models.Sale;
import com.andretask.salesmanagement.models.Transaction;
import com.andretask.salesmanagement.repositories.ClientRepository;
import com.andretask.salesmanagement.repositories.ProductRepository;
import com.andretask.salesmanagement.repositories.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public SaleService(SaleRepository saleRepository, ClientRepository clientRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    public List<SaleResponseDto> fetchSales() {
        List<Sale> sales = saleRepository.findAll();
        return sales.stream()
                .map(this::convertToSaleResponseDto)
                .collect(Collectors.toList());
    }




    public SaleResponseDto createSale(SaleCreateDto saleCreateDto) {
        Sale sale = new Sale();
        sale.setCreationDate(new Date());
        // Retrieve client and seller by ID
        Client client = clientRepository.findById(saleCreateDto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + saleCreateDto.getClientId()));
        sale.setClient(client);

        Client seller = clientRepository.findById(saleCreateDto.getSellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found with id: " + saleCreateDto.getSellerId()));
        sale.setSeller(seller);
        List<Transaction> transactions = new ArrayList<>();
        for (TransactionDto transactionDto : saleCreateDto.getTransactions()) {
            Transaction transaction = new Transaction();
            // Retrieve product by ID
            Product product = productRepository.findById(transactionDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + transactionDto.getProductId()));
            transaction.setProduct(product);
            transaction.setQuantity(transactionDto.getQuantity());
            transaction.setPrice(transactionDto.getPrice());
            transaction.setSale(sale);
            transactions.add(transaction);
        }
        sale.setTransactions(transactions);
        Sale savedSale = saleRepository.save(sale);

        SaleResponseDto responseDto = new SaleResponseDto();
        responseDto.setId(savedSale.getId());
        responseDto.setCreationDate(savedSale.getCreationDate());
        responseDto.setClientId(savedSale.getClient().getId());
        responseDto.setSellerId(savedSale.getSeller().getId());
        responseDto.setTotal(savedSale.getTotal());

        return responseDto;
    }

    public SaleResponseDto updateSale(Long id, SaleUpdateDto updateSaleDto) {
        Optional<Sale> optionalSale = saleRepository.findById(id);
        if (optionalSale.isPresent()) {
            Sale sale = optionalSale.get();
            for (TransactionUpdateDto updateTransactionDto : updateSaleDto.getTransactions()) {
                Transaction transaction = sale.getTransactions().stream()
                        .filter(t -> t.getId().equals(updateTransactionDto.getTransactionId()))
                        .findFirst()
                        .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
                if (updateTransactionDto.getQuantity() != null) {
                    transaction.setQuantity(updateTransactionDto.getQuantity());
                }
                if (updateTransactionDto.getPrice() != null) {
                    transaction.setPrice(updateTransactionDto.getPrice());
                }
            }
            Sale updatedSale = saleRepository.save(sale);
            return convertToSaleResponseDto(updatedSale);
        }
        return null;
    }

    private SaleResponseDto convertToSaleResponseDto(Sale sale) {
        SaleResponseDto responseDto = new SaleResponseDto();
        responseDto.setId(sale.getId());
        responseDto.setCreationDate(sale.getCreationDate());
        responseDto.setClientId(sale.getClient().getId());
        responseDto.setSellerId(sale.getSeller().getId());
        responseDto.setTotal(sale.getTotal());
        return responseDto;
    }

}
