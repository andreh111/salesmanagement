package com.andretask.salesmanagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobile;

    public Client() {
    }

    public Client(Long id, String name, String lastName, String mobile) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.mobile = mobile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}

