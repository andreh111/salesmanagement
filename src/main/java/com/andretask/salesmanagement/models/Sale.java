package com.andretask.salesmanagement.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Client seller;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public Sale(){}

    public Sale(Long id, Date creationDate, Client client, Client seller, List<Transaction> transactions) {
        this.id = id;
        this.creationDate = creationDate;
        this.client = client;
        this.seller = seller;
        this.transactions = transactions;
    }

    public double getTotal() {
        return transactions.stream().mapToDouble(Transaction::getTotal).sum();
    }

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getSeller() {
        return seller;
    }

    public void setSeller(Client seller) {
        this.seller = seller;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", client=" + client +
                ", seller=" + seller +
                ", transactions=" + transactions +
                '}';
    }
}

