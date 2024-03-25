package com.andretask.salesmanagement.controllers.dto;

import java.util.List;

public class SaleCreateDto {
    private Long clientId;
    private Long sellerId;
    private List<TransactionDto> transactions;

    public SaleCreateDto(Long clientId, Long sellerId, List<TransactionDto> transactions) {
        this.clientId = clientId;
        this.sellerId = sellerId;
        this.transactions = transactions;
    }

    // Getters and setters
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public List<TransactionDto> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDto> transactions) {
        this.transactions = transactions;
    }
}
