package com.andretask.salesmanagement.controllers.dto;
public class TransactionUpdateDto {
    private Long transactionId;
    private Integer quantity;
    private Double price;

    public TransactionUpdateDto(Long transactionId, Integer quantity, Double price) {
        this.transactionId = transactionId;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

