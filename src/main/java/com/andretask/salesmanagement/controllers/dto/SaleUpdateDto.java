package com.andretask.salesmanagement.controllers.dto;

import java.util.List;

public class SaleUpdateDto {
    private List<TransactionUpdateDto> transactions;

    // Getters and setters
    public List<TransactionUpdateDto> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionUpdateDto> transactions) {
        this.transactions = transactions;
    }
}
