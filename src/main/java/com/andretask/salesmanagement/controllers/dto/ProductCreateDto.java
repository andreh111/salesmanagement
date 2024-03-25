package com.andretask.salesmanagement.controllers.dto;

public class ProductCreateDto {
    private String name;
    private String description;
    private String category;

    public ProductCreateDto(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
