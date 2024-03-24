package com.andretask.salesmanagement.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Product is required")
    @ManyToOne
    private Product product;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @Min(value = 0, message = "Price must be non-negative")
    private double price;

    @ManyToOne
    private Sale sale;

    public double getTotal() {
        return quantity * price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                ", sale=" + sale +
                '}';
    }
}
