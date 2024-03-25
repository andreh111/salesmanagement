package com.andretask.salesmanagement.controllers.dto;
public class ProductResponseDto {
    private Long id;
    private String message;

    public ProductResponseDto(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

