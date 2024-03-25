package com.andretask.salesmanagement.controllers.dto;

public class ClientUpdateDto {
    private String name;
    private String lastName;
    private String mobile;

    public ClientUpdateDto(String name, String lastName, String mobile) {
        this.name = name;
        this.lastName = lastName;
        this.mobile = mobile;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
