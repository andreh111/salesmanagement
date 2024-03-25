package com.andretask.salesmanagement.controllers.dto;

import java.util.Date;
import java.util.List;

public class SaleResponseDto {
    private Long id;
    private Date creationDate;
    private Long clientId;
    private Long sellerId;
    private double total;
    public SaleResponseDto() {}

    public SaleResponseDto(Long id, Date creationDate, Long clientId, Long sellerId, double total) {
        this.id = id;
        this.creationDate = creationDate;
        this.clientId = clientId;
        this.sellerId = sellerId;
        this.total = total;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
